package com.qm.omp.business.invocation.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.iface.system.ISystemLogService;
/**
 * 系统管理，日志管理
 * @author rh
 * @date 2014-12-17 14:29:38
 *
 */
@Component("INVO_system")
public class SystemLogInvocation implements BaseInvocation{

	private final Logger             logger = Logger.getLogger(SystemLogInvocation.class);
	
	@Autowired
	private ISystemLogService systemLogService;
	
	/**
	 * 查询日志
	 * @param context
	 * @return
	*/
	public InvocationResult saveLog(InvocationContext context)
	{
	    // 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
//		int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
//		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
		String menuName = RequestUtil.getStrParamterAsDef(context.getRequest(), "menuName", "");
		String method = RequestUtil.getStrParamterAsDef(context.getRequest(), "method", "");
		String description = RequestUtil.getStrParamterAsDef(context.getRequest(), "description", "");
		String params = RequestUtil.getStrParamterAsDef(context.getRequest(), "params", "");
		Map<String, Object> log = new HashMap<String, Object>();
        UserInfoBean user = (UserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
        log.put("menuName", menuName);
        log.put("method", method);
        log.put("description", description);
        log.put("params", params);
        log.put("fTime", DateTimeUtil.getTodayChar14());
        if(user!=null){
        	log.put("fUserCode", user.getfUserCode());
        }
        String ipAddr =  context.getRequest().getRemoteHost();
        if(ipAddr!=null){
        	log.put("fIp",ipAddr);
        }
		try
		{
		    systemLogService.saveLog(log);
		}
		catch (Exception ex)
		{
		    logger.error("UserInvocation saveLog happen execption.", ex);
		        result.setRetCode(ISystemConstants.SYS_FAILED);
	    }
	    return result;
		
	}
 
	/**
	 * 查询日志
	 * @param context
	 * @return
	 */
	public InvocationResult queryLogList(InvocationContext context)
	{
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
		int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
		String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
		String fUserName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "beginTime", "");
		String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "endTime", "");
		
		try
		{
			Map<String, Object> queryMap = systemLogService.queryLogList(fUserCode,fUserName,beginTime,endTime, page, rows);
			result.setRetObj(queryMap);
		}
		catch (Exception ex)
		{
			logger.error("UserInvocation queryLogList happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}
	
	/**
	 * 删除日志
	 * @param context
	 * @return
	 */
	public InvocationResult deleteLog(InvocationContext context)
	{
		// 返回结果对象
		InvocationResult result = InvocationResult.newInstance();
		String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
		String fUserName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserName", "");
		String beginTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "beginTime", "");
		String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "endTime", "");
		
		try
		{
			systemLogService.deleteLog(fUserCode,fUserName,beginTime,endTime);
		}
		catch (Exception ex)
		{
			logger.error("UserInvocation deleteLog happen execption.", ex);
			result.setRetCode(ISystemConstants.SYS_FAILED);
		}
		return result;
	}
}
