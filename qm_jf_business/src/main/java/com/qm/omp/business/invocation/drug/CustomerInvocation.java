package com.qm.omp.business.invocation.drug;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.qm.omp.business.pojo.drug.Customer;
import com.qm.omp.business.service.impl.drug.CustomerServiceImpl;

/**
 * @ClassName: CustomerInvocation
 * @Description: 客户管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_customer")
public class CustomerInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(CustomerInvocation.class);
	
	@Autowired
	private CustomerServiceImpl customerService;
	

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
        String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
        String fCityName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fCityName", "");
        try
        {
        	Map<String, Object> map = customerService.getList(fName, page, rows, fCityName);
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("CustomerInvocation getList happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    /**
     * 
     * @Title: getListById
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getListById(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	int fCustomerId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fCustomerId", 0);
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fId", fCustomerId);
    	try
    	{
    		Map<String, Object> map = customerService.getListById(page, rows, params);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CustomerInvocation getListById happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 
     * @Title: getList_youXiao
     * @Description: (分页查询)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getList_youXiao(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fCityName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fCityName", "");
    	Map<String, Object> params = new HashMap<String, Object>();
    	UserInfoBean userInfo = (UserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
    	params.put("userId", userInfo.getId());
    	params.put("fName", fName);
    	params.put("fCityName", fCityName);
    	try
    	{
    		Map<String, Object> map = customerService.getList_youXiao(page, rows, params);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CustomerInvocation getList_youXiao happen execption.", ex);
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
    	
    	String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(), "fAddress", "");
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fUnit = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUnit", "");
    	String fPhone = RequestUtil.getStrParamterAsDef(context.getRequest(), "fPhone", "");
    	String fBeginTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "fBeginTime", "");
    	String fEndTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "fEndTime", "");
    	String fRemark = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRemark", "");
    	String fDrugPrinterIds = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDrugPrinterIds", "");
    	String fCompanyIds = RequestUtil.getStrParamterAsDef(context.getRequest(), "fCompanyIds", "");
    	String fBirthday = RequestUtil.getStrParamterAsDef(context.getRequest(), "fBirthday", "");
    	int fCityId = RequestUtil.getIntParamter(context.getRequest(), "fCityId");
    	Customer bean = new Customer();
    	bean.setfBirthday(fBirthday);
    	bean.setfUnit(fUnit);
    	bean.setfPhone(fPhone);
    	bean.setfBeginTime(fBeginTime.replaceAll("-", ""));
    	bean.setfEndTime(fEndTime.replaceAll("-", ""));
    	bean.setfRemark(fRemark);
    	bean.setfCityId(fCityId);
    	bean.setfAddress(fAddress);
    	bean.setfName(fName);
    	bean.setfState(0+"");
    	bean.setfTime(DateTimeUtil.getTodayChar8());
    	String[] arr = fDrugPrinterIds.split(",");
    	List<String> drugPrinterIds = Arrays.asList(arr);//客户售卖药品的种类权限
    	List<String> companyIds = Arrays.asList(fCompanyIds.split(","));
    	try
    	{
    		customerService.save(bean, drugPrinterIds, companyIds);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CustomerInvocation save happen execption.", ex);
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
    	
    	int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId", 0);
    	String fAddress = RequestUtil.getStrParamterAsDef(context.getRequest(), "fAddress", "");
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	String fUnit = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUnit", "");
    	String fPhone = RequestUtil.getStrParamterAsDef(context.getRequest(), "fPhone", "");
    	String fBeginTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "fBeginTime", "");
    	String fEndTime = RequestUtil.getStrParamterAsDef(context.getRequest(), "fEndTime", "");
    	String fRemark = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRemark", "");
    	int fCityId = RequestUtil.getIntParamter(context.getRequest(), "fCityId");
    	String fDrugPrinterIds = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDrugPrinterIds", "");
    	String fCompanyIds = RequestUtil.getStrParamterAsDef(context.getRequest(), "fCompanyIds", "");
    	String fBirthday = RequestUtil.getStrParamterAsDef(context.getRequest(), "fBirthday", "");
    	Customer bean = new Customer();
    	bean.setfBirthday(fBirthday);
    	bean.setfUnit(fUnit);
    	bean.setfPhone(fPhone);
    	bean.setfBeginTime(fBeginTime.replaceAll("-", ""));
    	bean.setfEndTime(fEndTime.replaceAll("-", ""));
    	bean.setfRemark(fRemark);
    	bean.setfCityId(fCityId);
    	bean.setfAddress(fAddress);
    	bean.setfName(fName);
    	bean.setfId(fId);
    	String[] arr = fDrugPrinterIds.split(",");
    	List<String> drugPrinterIds = Arrays.asList(arr);//客户售卖药品的种类权限
    	List<String> companyIds = Arrays.asList(fCompanyIds.split(","));
    	try
    	{
    		customerService.edit(bean, drugPrinterIds, companyIds);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CustomerInvocation edit happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    
    /**
     * 删除（软删除）
     * @param context
     * @return
     */
    public InvocationResult delete(InvocationContext context){
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	
    	int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId", 0);
    	Customer bean = new Customer();
    	bean.setfState(1+"");
    	bean.setfId(fId);
    	try
    	{
    		customerService.edit(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CustomerInvocation delete happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
   
}
