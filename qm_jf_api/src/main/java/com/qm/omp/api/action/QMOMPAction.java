package com.qm.omp.api.action;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.api.pojo.PageDynamicRequestInfo;
import com.qm.omp.api.pool.ExecutorPool;
import com.qm.omp.api.service.IResultMsgService;
import com.qm.omp.api.util.LogConstant;

/**
 * @ClassName: IitiyanOMPAction
 * @Description: 消费者统一管理平台Action
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
@Controller
@RequestMapping(value = "/actionDispatcher.do")
public class QMOMPAction
{
    protected static final Logger logger = LoggerFactory.getLogger(QMOMPAction.class);

    @Autowired
    private IResultMsgService     resultMsgService;

    /**
     * 统一入口
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "")
    public void defaultHandle(HttpServletRequest request, HttpServletResponse response)
    {
    	LogConstant.setRequest(request);
    	LogConstant.setResponse(response);
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try
        {
            String reqType = request.getParameter("reqType");
            if ("action".equals(reqType))
            {
                singleRequestForAction(request, response);
            }
            else
            {
                Object retObj = dispatchRequest(request, response);
                String s = JSON.toJSONString(retObj);
                PrintWriter pw = response.getWriter();
                pw.print(s);
                pw.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("QMOMPAction defaultHandle happen execption.", ex);
            throw new RuntimeException("UOMP REQUEST HANDLE ERROR!", ex);
        }
    }

    /**
     * 分发请求
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private Object dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object ret = null;
        String jsonParam = request.getParameter("jsonParam");
        if (!StringUtils.isBlank(jsonParam))
        {
            List<PageDynamicRequestInfo> requests = JSONArray.parseArray(jsonParam, PageDynamicRequestInfo.class);
            ret = multiRequests(requests, request, response);
        }
        else
        {
            ret = singleRequest(request, response);
        }
        return ret;
    }

    /**
     * 多个请求
     * 
     * @param requests
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private Object multiRequests(List<PageDynamicRequestInfo> requests, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {
        final Map<String, Object> ret = new HashMap<String, Object>();
        if (requests.size() > 1)
        {
            final CountDownLatch countDownLatch = new CountDownLatch(requests.size());
            List<FutureTask<Boolean>> taskList = new ArrayList<FutureTask<Boolean>>();
            for (int i = 0; i < requests.size(); i++)
            {
                final PageDynamicRequestInfo tmpReq = requests.get(i);
                FutureTask<Boolean> task = new FutureTask<Boolean>(new Callable<Boolean>()
                {
                    public Boolean call()
                    {
                        ret.put(tmpReq.getDynamicDataNodeName(), multiRequest(tmpReq, request, response));
                        countDownLatch.countDown();
                        return true;
                    }
                });
                taskList.add(task);
                ExecutorPool.getInstance().exe(task);
                // ExecutorPool.getInstance().exe(new Runnable() {
                // public void run() {
                // ret.put(tmpReq.getDynamicDataNodeName(), multiRequest(tmpReq,
                // request, response));
                // countDownLatch.countDown();
                // }
                // });
            }
            countDownLatch.await(60, TimeUnit.SECONDS);
            FutureTask<Boolean> tasked = null;
            for (int j = 0; j < taskList.size(); j++)
            {
                tasked = taskList.get(j);
                if (!tasked.isDone())
                {
                    tasked.cancel(true);
                }
            }
        }
        else
        {
            PageDynamicRequestInfo tmpReq = requests.get(0);
            ret.put(tmpReq.getDynamicDataNodeName(), multiRequest(tmpReq, request, response));
        }
        return ret;
    }

    /**
     * 处理多个请求
     * 
     * @param requestInfo
     * @param request
     * @param response
     * @return
     */
    private Object multiRequest(PageDynamicRequestInfo requestInfo, HttpServletRequest request, HttpServletResponse response)
    {
        InvocationResult result = null;
        try
        {
            InvocationContext reqContext = new InvocationContext(request, response);
            reqContext.setReqParam(requestInfo.getDynamicParameter());
            Object invocation = findService(requestInfo.getDynamicURI(), request);
            String reqMethod = requestInfo.getDynamicParameter().get("reqMethod");
            Method m = invocation.getClass().getMethod(reqMethod, new Class[] {InvocationContext.class});
            result = (InvocationResult) m.invoke(invocation, new Object[] {reqContext});
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = InvocationResult.newInstance(ISystemConstants.SYS_FAILED);
        }
        finally
        {
            genResultMsg(result);
        }

        return result;
    }

    /**
     * 单个请求
     * 
     * @param request
     * @param response
     * @return
     */
    private Object singleRequest(HttpServletRequest request, HttpServletResponse response)
    {
        InvocationResult result = null;
        try
        {
            InvocationContext reqContext = new InvocationContext(request, response);
            String reqUrl = request.getParameter("reqUrl");
            Object invocation = findService(reqUrl, request);
            String reqMethod = request.getParameter("reqMethod");
            Method m = invocation.getClass().getMethod(reqMethod, new Class[] {InvocationContext.class});
            result = (InvocationResult) m.invoke(invocation, new Object[] {reqContext});
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = InvocationResult.newInstance(ISystemConstants.SYS_FAILED);
        }
        finally
        {
            genResultMsg(result);
        }
        return result;
    }

    /**
     * 单个请求
     * 
     * @param request
     * @param response
     * @return
     */
    private void singleRequestForAction(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            InvocationContext reqContext = new InvocationContext(request, response);
            String reqUrl = request.getParameter("reqUrl");
            Object invocation = findService(reqUrl, request);
            String reqMethod = request.getParameter("reqMethod");
            Method m = invocation.getClass().getMethod(reqMethod, new Class[] {InvocationContext.class});
            m.invoke(invocation, new Object[] {reqContext});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
    }

    /**
     * 查找Handler
     * 
     * @param request
     * @return
     */
    private Object findService(String reqUrl, HttpServletRequest request)
    {
        if (reqUrl.startsWith("/"))
        {
            reqUrl = reqUrl.substring(1);
        }
        reqUrl = "INVO_" + reqUrl.replaceAll("\\/", "_");
        ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        return springContext.getBean(reqUrl);
    }

    /**
     * 构造返回提示信息
     * 
     * @param result
     */
    private void genResultMsg(InvocationResult result)
    {
        resultMsgService.formatResultMsg(result);
    }
}
