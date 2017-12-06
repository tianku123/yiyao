package com.qm.omp.business.invocation.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.impl.drug.OrderDetailServiceImpl;
import com.qm.omp.business.util.DateTimeUtil;

/**
 * @ClassName: OrderDetailInvocation
 * @Description: 发货单详细管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_orderDetail")
public class OrderDetailInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(OrderDetailInvocation.class);
	
	@Autowired
	private OrderDetailServiceImpl orderDetailService;

    /**
     * 主管有权限的提成
     * @Title: getList_zg
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getList_zg(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
        int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
        int fYwyId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fYwyId", 0);
        String fOrderId = RequestUtil.getStrParamterAsDef(context.getRequest(), "fOrderId", "");
    	UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("userId", userInfo.getId());
    	params.put("fOrderId", fOrderId);
    	params.put("fYwyId", fYwyId);
        try
        {
        	Map<String, Object> map = this.orderDetailService.getList_zg(params, page, rows, userInfo.getfUserRoleRel());
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("OrderInvocation getList_zg happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
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
    	try
    	{
    		Map<String, Object> map = this.orderDetailService.getList(fOrderId, page, rows);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("OrderInvocation getList happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    /**
     * 
     * @Title: getList_EditOrder
     * @Description: (分页查询) 供订单编辑页面使用
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getList_EditOrder(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	String fOrderId = RequestUtil.getStrParamterAsDef(context.getRequest(), "fOrderId", "");
    	try
    	{
    		Map<String, Object> map = this.orderDetailService.getList_EditOrder(fOrderId, page, rows);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("OrderInvocation getList_EditOrder happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 
     * @Title: printer
     * @Description: (打印发货单)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult printer(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	String fOrderId = RequestUtil.getStrParamterAsDef(context.getRequest(), "fOrderId", "");
    	try
    	{
    		Map<String, Object> map = new HashMap<String, Object>();
    		List<Map<String, Object>> list = this.orderDetailService.printer(fOrderId);
    		if(list != null && list.size()>0){
    			Double fMoneyKaipiao = 0d;
    			for(Map<String, Object> bean : list){
    				fMoneyKaipiao = MapUtils.getDouble(bean, "F_KAIPIAO_PRICE", 0d) * MapUtils.getIntValue(bean, "F_NUMBER");
    				bean.put("F_MONEY_KAIPIAO", fMoneyKaipiao);
    			}
    			Map<String, Object> mm = list.get(0);
    			//收货人
    			map.put("fOrderId", MapUtils.getString(mm, "F_ORDER_ID"));
    			map.put("fName", MapUtils.getString(mm, "SHOUHUO_NAME"));
    			map.put("fPhone", MapUtils.getString(mm, "F_PHONE"));
    			map.put("fAddress", MapUtils.getString(mm, "SHOUHUO_ADDRESS"));
    			map.put("fUnit", MapUtils.getString(mm, "F_UNIT"));
    			
    		}
    		map.put("list", list);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("OrderInvocation printer happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }

    /**
     * 订单详情
     * @Title: getOrderDetailList
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getOrderDetailList(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	// 默认状态1，未审核
    	String fState = RequestUtil.getStrParamterAsDef(context.getRequest(), "fState", "");
    	// 付款情况
    	String fPaymentState = RequestUtil.getStrParamterAsDef(context.getRequest(), "fPaymentState", "");
    	// 财务审核情况
    	String fExamine = RequestUtil.getStrParamterAsDef(context.getRequest(), "fExamine", "");
    	// 是否含税
    	String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(), "fTax", "");
    	// 政策报单
    	String fIsPolicy = RequestUtil.getStrParamterAsDef(context.getRequest(), "fIsPolicy", "");
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fCustomName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fCustomName", "");
    	String beginTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "beginTime", "");
    	String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "endTime", "");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fName", fName);
    	params.put("fCustomName", fCustomName);
    	params.put("fState", fState);
    	params.put("fPaymentState", fPaymentState);
    	params.put("fExamine", fExamine);
    	params.put("fTax", fTax);
    	params.put("fIsPolicy", fIsPolicy);
    	params.put("beginTime", DateTimeUtil.get_yyyyMMddHHmmss(beginTime));
    	params.put("endTime", DateTimeUtil.get_yyyyMMddHHmmss(endTime));
    	try
    	{
    		Map<String, Object> map = orderDetailService.getOrderDetailList(page, rows, params);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("OrderInvocation getList_cw happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
	
    
    /**
     * 合计金额
     * @Title: getTotalMoney
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getTotalMoney(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	// 默认状态1，未审核
    	String fState = RequestUtil.getStrParamterAsDef(context.getRequest(), "fState", "");
    	// 付款情况
    	String fPaymentState = RequestUtil.getStrParamterAsDef(context.getRequest(), "fPaymentState", "");
    	// 财务审核情况
    	String fExamine = RequestUtil.getStrParamterAsDef(context.getRequest(), "fExamine", "");
    	// 是否含税
    	String fTax = RequestUtil.getStrParamterAsDef(context.getRequest(), "fTax", "");
    	// 政策报单
    	String fIsPolicy = RequestUtil.getStrParamterAsDef(context.getRequest(), "fIsPolicy", "");
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fCustomName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fCustomName", "");
    	String beginTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "beginTime", "");
    	String endTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "endTime", "");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fName", fName);
    	params.put("fCustomName", fCustomName);
    	params.put("fState", fState);
    	params.put("fPaymentState", fPaymentState);
    	params.put("fExamine", fExamine);
    	params.put("fTax", fTax);
    	params.put("fIsPolicy", fIsPolicy);
    	params.put("beginTime", DateTimeUtil.get_yyyyMMddHHmmss(beginTime));
    	params.put("endTime", DateTimeUtil.get_yyyyMMddHHmmss(endTime));
    	try
    	{
    		Map<String, Object> map = orderDetailService.getTotalMoney(page, rows, params);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("OrderInvocation getTotalMoney happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
}
