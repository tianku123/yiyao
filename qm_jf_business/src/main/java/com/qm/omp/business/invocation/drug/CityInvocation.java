package com.qm.omp.business.invocation.drug;

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
import com.qm.omp.business.pojo.drug.City;
import com.qm.omp.business.service.impl.drug.CityServiceImpl;

/**
 * @ClassName: CityInvocation
 * @Description: 客户区域管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_city")
public class CityInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(CityInvocation.class);
	
	@Autowired
	private CityServiceImpl cityService;
	

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
        try
        {
        	Map<String, Object> map = cityService.getList(fName, page, rows);
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("CityInvocation getList happen execption.", ex);
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
    	
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	City bean = new City();
    	bean.setfName(fName);
    	bean.setfState(0+"");
    	bean.setfTime(DateTimeUtil.getTodayChar14());
    	try
    	{
    		cityService.save(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CityInvocation save happen execption.", ex);
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
    	String fName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fName", "");
    	City bean = new City();
    	bean.setfId(fId);
    	bean.setfName(fName);
    	try
    	{
    		cityService.edit(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CityInvocation edit happen execption.", ex);
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
    	City bean = new City();
    	bean.setfState(1+"");
    	bean.setfId(fId);
    	try
    	{
    		cityService.delete(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("CityInvocation delete happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
   
}
