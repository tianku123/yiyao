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
import com.qm.omp.business.pojo.admin.ZgYWYBean;
import com.qm.omp.business.service.impl.drug.ZgYWYServiceImpl;

/**
 * @ClassName: ZgYWYInvocation
 * @Description: 主管业务员关联关系
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_zgywy")
public class ZgYWYInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(ZgYWYInvocation.class);
	
	@Autowired
	private ZgYWYServiceImpl zgYwyService;
	

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
    	String fZhuGuanId = RequestUtil.getStrParamterAsDef(context.getRequest(), "fZhuGuanId", "");
    	String fUserName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserName", "");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("fZhuGuanId", fZhuGuanId);
    	params.put("fUserName", fUserName);
    	try
    	{
    		Map<String, Object> map = zgYwyService.getList(params, page, rows);
    		result.setRetObj(map);
    	}
    	catch (Exception ex)
    	{
    		logger.error("ZgYWYInvocation getList happen execption.", ex);
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
    	String fYWYIds = RequestUtil.getStrParamter(context.getRequest(), "fYWYIds");
    	String departments = RequestUtil.getStrParamter(context.getRequest(), "departments");
    	int fZhuGuanId = RequestUtil.getIntParamter(context.getRequest(), "fZhuGuanId");
    	try
    	{
    		/**
    		 * 业务员实际只会有一个
    		 */
    		String[] arr = fYWYIds.split(",");
    		/**
    		 * 一个业务员对应多个部门
    		 */
    		String[] departmentArr = departments.split(",");
    		for (String fYWYId : arr) {
    			for (String department : departmentArr) {
    				ZgYWYBean bean = new ZgYWYBean();
    				bean.setfYWYId(Integer.parseInt(fYWYId));
    				bean.setfZhuGuanId(fZhuGuanId);
    				bean.setfDepartmentId(Integer.parseInt(department));
    				bean.setfTime(DateTimeUtil.getTodayChar14());
    				zgYwyService.save(bean);
    			}
    		}
    	}
    	catch (Exception ex)
    	{
    		logger.error("ZgYWYInvocation save happen execption.", ex);
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
    	try
    	{
    		zgYwyService.delete(fId);
    	}
    	catch (Exception ex)
    	{
    		logger.error("ZgYWYInvocation delete happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
   
}
