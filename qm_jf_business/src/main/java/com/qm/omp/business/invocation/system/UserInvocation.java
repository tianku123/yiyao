package com.qm.omp.business.invocation.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.DateTimeUtil;
import com.qm.common.util.Md5Util;
import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.iface.system.IUserService;
@Component("INVO_user")
public class UserInvocation implements BaseInvocation {

	 private final Logger             logger = Logger.getLogger(UserInvocation.class);

	    @Autowired
	    private IUserService userService;
	    /**
	     * 
	     * @Title: queryYWYList
	     * @Description: ()
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult queryYWYList(InvocationContext context)
	    {
	        // 返回结果对象
	        InvocationResult result = InvocationResult.newInstance();
	        int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
	        int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 0);
	        String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
	        String fZhuGuanId = RequestUtil.getStrParamterAsDef(context.getRequest(), "fZhuGuanId", "");
	        Map<String, Object> param = new HashMap<String, Object>();
	        param.put("fUserCode", fUserCode);
	        try
	        {
//	        	String role = this.userService.queryRoleCodeById(fZhuGuanId);
//	        	if (role == null) {
//	        		param.put("roleCode", "");
//	        	} else if ("daquzhuguan".equals(role)) {
//	        		param.put("roleCode", "xiaoquzhuguan");
//	        	} else if ("xiaoquzhuguan".equals(role)) {
//	        		param.put("roleCode", "yewuyuan");
//	        	}
	        	param.put("roleCode", "yewuyuan");
	            Map<String, Object> queryMap = userService.queryYWYList(param, page, rows);
	            result.setRetObj(queryMap);
	        }
	        catch (Exception ex)
	        {
	            logger.error("UserInvocation queryUserList happen execption.", ex);
	            result.setRetCode(ISystemConstants.SYS_FAILED);
	        }
	        return result;
	    }
	    /**
	     * 
	     * @Title: queryUserList
	     * @Description: (根据条件查询用户信息)
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult queryUserList(InvocationContext context)
	    {
	    	// 返回结果对象
	    	InvocationResult result = InvocationResult.newInstance();
	    	int page = RequestUtil.getIntParamterAsDef(context.getRequest(), "page", 1);
	    	int rows = RequestUtil.getIntParamterAsDef(context.getRequest(), "rows", 0);
	    	String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
	    	String fUserName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserName", "");
	    	String roleCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "roleCode", "");
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("fUserCode", fUserCode);
	    	param.put("fUserName", fUserName);
	    	param.put("roleCode", roleCode);
	    	try
	    	{
	    		Map<String, Object> queryMap = userService.queryUserList(param, page, rows);
	    		result.setRetObj(queryMap);
	    	}
	    	catch (Exception ex)
	    	{
	    		logger.error("UserInvocation queryUserList happen execption.", ex);
	    		result.setRetCode(ISystemConstants.SYS_FAILED);
	    	}
	    	return result;
	    }
	    
	    
	    /**
	     * 
	     * @Title: queryUserById
	     * @Description: 根据用户编号查询用户
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult queryUserById(InvocationContext context)
	    {
	        // 返回结果对象
	        InvocationResult result = InvocationResult.newInstance();
	        String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "code", "");

	        Map<String, Object> param = new HashMap<String, Object>();
	        param.put("fUserCode", fUserCode);
	        try
	        {
	            UserInfoBean muif = userService.queryUserById(param);
	            result.setRetObj(muif);
	        }
	        catch (Exception ex)
	        {
	            logger.error("UserInvocation queryUserById happen execption.", ex);
	            result.setRetCode(ISystemConstants.SYS_FAILED);
	        }
	        return result;
	    }
	    
	 
	    /**
	     * 
	     * @Title: addUser
	     * @Description: (新增用户)
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult addUser(InvocationContext context)
	    {
	        // 返回结果对象
	        InvocationResult result = InvocationResult.newInstance();
	        String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
	        String fUserName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserName", "");
	        String fUserPwd = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserPwd", "");
	        String fUserRoleRel = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserRoleRel", "");
	        String fUserTel = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserTel", "");
	        int fUserType = RequestUtil.getIntParamterAsDef(context.getRequest(), "fUserType", 0);
	        int fCityId = RequestUtil.getIntParamter(context.getRequest(), "fCityId");
	        String fDepartmentIds = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDepartmentIds", "");
	        fUserPwd = Md5Util.toMD5(fUserPwd);
	        UserInfoBean userInfoBean = new UserInfoBean();
	        userInfoBean.setfUserCode(fUserCode);
	        userInfoBean.setfUserName(fUserName);
	        userInfoBean.setfUserPwd(fUserPwd);
	        userInfoBean.setfUserRoleRel(fUserRoleRel);
	        userInfoBean.setfUserState(0);
	        userInfoBean.setfUserType(fUserType);
	        userInfoBean.setfUserTel(fUserTel);
	        userInfoBean.setfUserCreattime(DateTimeUtil.getTodayChar14());
	        userInfoBean.setfCityId(fCityId);
	        // 部门
//	        userInfoBean.setfDepartmentId(fDepartmentId);
	        String[] arr = fDepartmentIds.split(",");
	    	List<String> fDepartmentIdLists = Arrays.asList(arr);//部门
	        try
	        {
	            userService.addUser(userInfoBean, fDepartmentIdLists);
	        }
	        catch (Exception ex)
	        {
	            logger.error("UserInvocation addUser happen execption.", ex);
	            result.setRetCode(ISystemConstants.SYS_FAILED);
	        }
	        return result;
	    }


	    /**
	     * 
	     * @Title: editUser
	     * @Description: (编辑用户)
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult editUser(InvocationContext context)
	    {
	        // 返回结果对象
	        InvocationResult result = InvocationResult.newInstance();
//	        String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
	        String fUserName = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserName", "");
	        String fUserRoleRel = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserRoleRel", "");
	        String fUserTel = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserTel", "");
//	        String fPwd = RequestUtil.getStrParamterAsDef(context.getRequest(), "fPwd", "");
	        int fUserType = RequestUtil.getIntParamterAsDef(context.getRequest(), "fUserType", 0);
	        int fId = RequestUtil.getIntParamterAsDef(context.getRequest(), "fId", 0);
	        int fCityId = RequestUtil.getIntParamter(context.getRequest(), "fCityId");
	        String fDepartmentIds = RequestUtil.getStrParamterAsDef(context.getRequest(), "fDepartmentIds", "");
//	        fPwd = Md5Util.toMD5(fPwd);
	        UserInfoBean userInfoBean = new UserInfoBean();
	        userInfoBean.setId(fId);
	        userInfoBean.setfUserName(fUserName);
	        userInfoBean.setfUserRoleRel(fUserRoleRel);
	        userInfoBean.setfUserState(0);
	        userInfoBean.setfUserType(fUserType);
	        userInfoBean.setfUserTel(fUserTel);
//	        userInfoBean.setfUserPwd(fPwd);
	        userInfoBean.setfUserCreattime(DateTimeUtil.getTodayChar14());
	        userInfoBean.setfCityId(fCityId);
	        // 部门
//	        userInfoBean.setfDepartmentId(fDepartmentId);
	        String[] arr = fDepartmentIds.split(",");
	    	List<String> fDepartmentIdLists = Arrays.asList(arr);//部门
	        try
	        {
	            userService.editUser(userInfoBean, fDepartmentIdLists);
	        }
	        catch (Exception ex)
	        {
	            logger.error("UserInvocation editUser happen execption.", ex);
	            result.setRetCode(ISystemConstants.SYS_FAILED);
	        }
	        return result;
	    }
	    
	    /**
	     * 
	     * @Title: deleteUser
	     * @Description: (删除商户用户)
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult deleteUser(InvocationContext context)
	    {
	        // 返回结果对象
	        InvocationResult result = InvocationResult.newInstance();
//	        String fUserCode = RequestUtil.getStrParamterAsDef(context.getRequest(), "fUserCode", "");
	        int fId = RequestUtil.getIntParamter(context.getRequest(), "fId");
	        Map<String, Object> param = new HashMap<String, Object>();
	        param.put("id", fId);
	       
	        try
	        {
	            userService.deleteUser(param);
	        }
	        catch (Exception ex)
	        {
	            logger.error("UserInvocation deleteUser happen execption.", ex);
	            result.setRetCode(ISystemConstants.SYS_FAILED);
	        }
	        return result;
	    }
	    
	    /**
	     * 
	     * @Title: modifyUserPasswd
	     * @Description: (修改密码)
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult modifyUserPasswd(InvocationContext context)
	    {
	    	// 返回结果对象
	    	InvocationResult result = InvocationResult.newInstance();
	    	String comfiPasswd = RequestUtil.getStrParamterAsDef(context.getRequest(), "comfiPasswd", "");
	    	UserInfoBean user = (UserInfoBean) SessionUtil.getObjectAttribute(context.getRequest(), RequestConstants.ADMIN_SESSION_KEY);
	    	comfiPasswd = Md5Util.toMD5(comfiPasswd);
	    	try
	    	{
	    		userService.modifyUserPasswd(user.getId(),comfiPasswd);
	    	}
	    	catch (Exception ex)
	    	{
	    		logger.error("UserInvocation modifyUserPasswd happen execption.", ex);
	    		result.setRetCode(ISystemConstants.SYS_FAILED);
	    	}
	    	return result;
	    }
	    
	    /**
	     * 
	     * @Title: modifyPassword
	     * @Description: (重置用户密码为：123456)
	     * @param context
	     * @return
	     * @return InvocationResult 返回类型
	     * @throws
	     */
	    public InvocationResult modifyPassword(InvocationContext context)
	    {
	    	// 返回结果对象
	    	InvocationResult result = InvocationResult.newInstance();
	    	String pwd = Md5Util.toMD5("123456");
	    	int fId = RequestUtil.getIntParamter(context.getRequest(), "fId");
	    	
	    	try
	    	{
	    		userService.modifyPassword(fId,pwd);
	    	}
	    	catch (Exception ex)
	    	{
	    		logger.error("UserInvocation modifyPassword happen execption.", ex);
	    		result.setRetCode(ISystemConstants.SYS_FAILED);
	    	}
	    	return result;
	    }
	 
}
