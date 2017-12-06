package com.qm.omp.business.invocation.drug;

import java.util.List;
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
import com.qm.omp.business.pojo.drug.Department;
import com.qm.omp.business.service.impl.drug.DepartmentServiceImpl;

/**
 * @ClassName: DepartmentInvocation
 * @Description: 部门管理
 * @author rh
 * @date 2016-9-6 14:31:53
 */
@Component("INVO_department")
public class DepartmentInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(DepartmentInvocation.class);
	
	@Autowired
	private DepartmentServiceImpl departmentService;
	

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
        	Map<String, Object> map = departmentService.getList(fName, page, rows);
            result.setRetObj(map);
        }
        catch (Exception ex)
        {
            logger.error("DepartmentInvocation getList happen execption.", ex);
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
    	Department bean = new Department();
    	bean.setfName(fName);
    	bean.setfState(0+"");
    	bean.setfTime(DateTimeUtil.getTodayChar14());
    	try
    	{
    		departmentService.save(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DepartmentInvocation save happen execption.", ex);
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
    	Department bean = new Department();
    	bean.setfId(fId);
    	bean.setfName(fName);
    	try
    	{
    		departmentService.edit(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DepartmentInvocation edit happen execption.", ex);
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
    	Department bean = new Department();
    	bean.setfState(1+"");
    	bean.setfId(fId);
    	try
    	{
    		departmentService.delete(bean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("DepartmentInvocation delete happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }

    /**
     * 
     * @Title: getDepartmentByUserId
     * @Description: (获取用户所拥有的部门)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getDepartmentByUserId(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
        int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 50);
        int userId = RequestUtil.getIntParamterAsDef(context.getRequest(), "userId", 0);
        try
        {
        	List<Map<String, Object>> list = departmentService.getDepartmentByUserId(userId);
            result.setRetObj(list);
        }
        catch (Exception ex)
        {
            logger.error("DepartmentInvocation getDepartmentByUserId happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
}
