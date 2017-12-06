package com.qm.omp.business.invocation.drug;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.drug.Order;
import com.qm.omp.business.pojo.drug.Repayment;
import com.qm.omp.business.service.impl.drug.OrderServiceImpl;
import com.qm.omp.business.service.impl.drug.RepaymentService;

/**
 * @ClassName: RepaymentInvocation
 * @Description: 还款记录
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_repayment")
public class RepaymentInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(RepaymentInvocation.class);
	
	@Autowired
	private RepaymentService repaymentService;
	
	@Autowired
	private OrderServiceImpl orderService;

    /**
     * 
     * @Title: getList
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getList(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
        int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
        String fOrderId = RequestUtil.getStrParamterAsDef(context.getRequest(), "fOrderId", "");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fOrderId", fOrderId);
        try
        {
        	Map<String, Object> map = repaymentService.getList(params, page, rows);
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("RepaymentInvocation getList happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
    /**
     * 添加bean
     * @param context
     * @return
     */
    public InvocationResult save(InvocationContext context){
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int fSource = RequestUtil.getIntParamter(context.getRequest(), "fSource");
    	// 0:未还清，1：还清
    	int fPayOff = RequestUtil.getIntParamter(context.getRequest(), "fPayOff");
    	String fMoney = RequestUtil.getStrParamterAsDef(context.getRequest(), "fMoney", "0");
    	String fOrderId = RequestUtil.getStrParamter(context.getRequest(), "fOrderId");
    	String fIntro = RequestUtil.getStrParamter(context.getRequest(), "fIntro");
    	String fTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "fTime","");
    	Repayment bean = new Repayment();
    	bean.setfMoney(Double.parseDouble(fMoney));
    	bean.setfOrderId(fOrderId);
    	bean.setfSource(fSource);
    	bean.setfIntro(fIntro);
    	bean.setfTime(fTime.replaceAll("-", ""));
    	try
    	{
    		repaymentService.save(bean);
    		/**
    		 * 还清，则更新订单状态为已付款
    		 */
    		if (fPayOff == 1) {
    			Order order = new Order();
    			order.setfId(fOrderId);
    			order.setfPaymentState("2");
    			order.setfPaymentTime(DateTimeUtil.getTodayChar14());
    			this.orderService.edit(order);
    		}
    	}
    	catch (Exception ex)
    	{
    		logger.error("RepaymentInvocation save happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 编辑
     * @param context
     * @return
     */
    public InvocationResult edit(InvocationContext context){
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	
    	int fId = RequestUtil.getIntParamter(context.getRequest(), "fId");
    	int fSource = RequestUtil.getIntParamter(context.getRequest(), "fSource");
    	String fMoney = RequestUtil.getStrParamterAsDef(context.getRequest(), "fMoney", "0");
    	String fOrderId = RequestUtil.getStrParamter(context.getRequest(), "fOrderId");
    	String fIntro = RequestUtil.getStrParamter(context.getRequest(), "fIntro");
    	String fTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "fTime","");
    	Repayment bean = new Repayment();
    	bean.setfId(fId);
    	bean.setfMoney(Double.parseDouble(fMoney));
    	bean.setfOrderId(fOrderId);
    	bean.setfSource(fSource);
    	bean.setfIntro(fIntro);
    	bean.setfTime(fTime.replaceAll("-", ""));
    	try
    	{
    		repaymentService.edit(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("RepaymentInvocation edit happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    

    
}
