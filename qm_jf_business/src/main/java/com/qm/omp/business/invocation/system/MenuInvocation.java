package com.qm.omp.business.invocation.system;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.RequestUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.service.iface.admin.IMenuService;
import com.qm.omp.business.service.iface.system.IRoleService;

/**
 * @ClassName: MenuInvocation
 * @Description: 菜单权限URL
 * @author rh
 * @date  2014-12-1 20:16:27
 */
@Component("INVO_menu")
public class MenuInvocation implements BaseInvocation {

	private Logger  logger = Logger.getLogger(MenuInvocation.class);
	
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private IRoleService roleService;
	

    /**
     * 
     * @Title: modifyFuncQX
     * @Description: (更改角色对应的URL)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult modifyFuncQX(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        String roleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "roleCode", "");
        String _urlId = RequestUtil.getStrParamterAsDef(context.getRequest(), "_urlId", "");

        try
        {
            menuService.modifyFuncQX(roleCode,_urlId);//先删除该角色对应的URL映射，建立角色、URL映射
        }
        catch (Exception ex)
        {
            logger.error("MenuInvocation modifyFuncQX happen execption.", ex);
            result.setRetCode(ISystemConstants.SYS_FAILED);
        }
        return result;
    }
    
    /**
     * 
     * @Title: modifyFuncQX_Terminal
     * @Description: (更改终端角色对应的URL)
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult modifyFuncQX_Terminal(InvocationContext context)
    {
    	// 返回结果对象
    	InvocationResult result = InvocationResult.newInstance();
    	String roleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "roleCode", "");
    	String _urlId = RequestUtil.getStrParamterAsDef(context.getRequest(), "_urlId", "");
    	
    	try
    	{
    		menuService.modifyFuncQX_Terminal(roleCode,_urlId);//先删除该角色对应的URL映射，建立角色、URL映射
    	}
    	catch (Exception ex)
    	{
    		logger.error("MenuInvocation modifyFuncQX_Terminal happen execption.", ex);
    		result.setRetCode(ISystemConstants.SYS_FAILED);
    	}
    	return result;
    }
    
}
