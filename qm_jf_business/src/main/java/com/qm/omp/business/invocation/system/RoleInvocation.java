package com.qm.omp.business.invocation.system;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.RequestUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.system.RoleInfoBean;
import com.qm.omp.business.service.iface.system.IRoleService;

/**
 * @ClassName: RoleInvocation
 * @Description: 角色管理
 * @author rh
 * @date  2014-11-28 10:21:55
 */
@Component("INVO_role")
public class RoleInvocation implements BaseInvocation{
	private Logger  logger = Logger.getLogger(RoleInvocation.class);
	
	@Autowired
	private IRoleService roleService;
	

    /**
     * 
     * @Title: queryRoleDetail
     * @Description: (查询角色详情)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult queryRoleDetail(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        String roleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "roleCode", "");
//        MerchantRoleInfoBean RoleInfo = (RoleInfoBean) SessionUtil.getObjectAttribute(context.getRequest(),
//                RequestConstants.ADMIN_SESSION_KEY);
        try
        {
            RoleInfoBean roleInfoBean = roleService.queryRoleDetail(roleCode);
            result.setRetObj(roleInfoBean);
        }
        catch (Exception ex)
        {
            logger.error("RoleInvocation queryRoleDetail happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
    
    /**
     * 
     * @Title: queryRoleById
     * @Description: 根据角色编码查询角色
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult queryRoleById(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        String fRoleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "code", "");

        try
        {
            RoleInfoBean muif = roleService.queryRoleDetail(fRoleCode);
            result.setRetObj(muif);
        }
        catch (Exception ex)
        {
            logger.error("RoleInvocation editRole happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
    /**
     * 
     * @Title: addRole
     * @Description: (新增角色)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult addRole(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        String fRoleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRoleCode", "");
        String fRoleName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRoleName", "");
        int roleType = RequestUtil.getIntParamter(context.getRequest(), "fRoleType");

        RoleInfoBean roleInfoBean = new RoleInfoBean();
        roleInfoBean.setRoleCode(fRoleCode);
        roleInfoBean.setRoleName(fRoleName);
        roleInfoBean.setRoleDate(DateTimeUtil.getTodayChar14());
        roleInfoBean.setRoleType(roleType);
        try
        {
            roleService.addRole(roleInfoBean);
        }
        catch (Exception ex)
        {
            logger.error("RoleInvocation addRole happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    
    /**
     * 
     * @Title: editRole
     * @Description: (修改角色名)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult editRole(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	String fRoleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRoleCode", "");
    	String fRoleName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fRoleName", "");
    	
    	RoleInfoBean roleInfoBean = new RoleInfoBean();
    	roleInfoBean.setRoleCode(fRoleCode);
    	roleInfoBean.setRoleName(fRoleName);
    	roleInfoBean.setRoleDate(DateTimeUtil.getTodayChar14());
    	try
    	{
    		roleService.editRole(roleInfoBean);
    	}
    	catch (Exception ex)
    	{
    		logger.error("RoleInvocation editRole happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
    /**
     * 
     * @Title: deleteRole
     * @Description: (删除角色，同时删除角色对应的url映射信息)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult deleteRole(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	String fRoleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "roleCode", "");
    	
    	try
    	{
    		roleService.deleteRole(fRoleCode);//删除角色 ,同时删除角色对应的url映射信息
    	}
    	catch (Exception ex)
    	{
    		logger.error("RoleInvocation deleteRole happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }


}
