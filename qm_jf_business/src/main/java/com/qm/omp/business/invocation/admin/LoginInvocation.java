package com.qm.omp.business.invocation.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qm.common.util.Md5Util;
import com.qm.common.util.RequestUtil;
import com.qm.common.util.SessionUtil;
import com.qm.omp.api.constants.RequestConstants;
import com.qm.omp.api.invocation.BaseInvocation;
import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;
import com.qm.omp.business.constants.LogicCodeConstants;
import com.qm.omp.business.constants.SystemCodeConstants;
import com.qm.omp.business.pojo.admin.MenuInfoBean;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.iface.admin.ILoginService;
import com.qm.omp.business.service.iface.admin.IMenuService;

/**
 * @ClassName: LoginInvocation
 * @Description: 用户登录/退出处理
 * @author rh
 * @date 2014-12-15 09:33:54
 */
@Component("INVO_login")
public class LoginInvocation implements BaseInvocation
{
    private Logger               logger = Logger.getLogger(LoginInvocation.class);

    @Autowired
    private ILoginService        loginService;
    
    @Autowired
    private IMenuService menuService;

    
	/**
     * @Title: login
     * @Description: 用户登录
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult login(InvocationContext context)
    {
        long begin = System.currentTimeMillis();
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        HttpServletRequest request = context.getRequest();
        // 登陆帐号
        String loginId = RequestUtil.getStrParamter(context.getRequest(), "loginId");
        // 登陆密码 MD5
        String loginPwd = request.getParameter("loginPwd");
        // 登陆验证码
        //String loginCode = request.getParameter("loginCode");
        // 系统获取验证码
        //String sessionCode = "";
        try
        {
            // 从Session中获取验证码
            //sessionCode = (String) SessionUtil.getObjectAttribute(context.getRequest(), RequestConstants.ADMIN_CHECK_CODE);

            // 设置验证码失效
           // SessionUtil.removeObjectAttribute(context.getRequest(), RequestConstants.ADMIN_CHECK_CODE);
            // 判断验证码是否正确
            //if (!loginCode.equals(sessionCode))
            //{
            //    result.setLogicCode(LogicCodeConstants.VERIFICATION_CODE_ERROR);
            //    return result;
            //}

            // 判断登陆信息有效性 判断登陆密码是否有效
            if (("".equals(loginId)) || ("".equals(loginPwd)))
            {
                result.setLogicCode(LogicCodeConstants.LOGIN_PWD_EMPTY);
                return result;
            }
            
            // 从数据库中获取管理员信息
            UserInfoBean userInfo = (UserInfoBean) loginService.login(loginId);
            // 判断是否取到密码
            if (userInfo == null)
            {
                result.setLogicCode(LogicCodeConstants.NOT_USER);
                return result;
            }
           
            // 判断密码是否正确
            else if (!Md5Util.toMD5(loginPwd).equals(userInfo.getfUserPwd()))
            {
                result.setLogicCode(LogicCodeConstants.LOGIN_PWD_ERROR);
                return result;
            }

//            // 判断管理状态
//            if (!"1".equals(userInfo.getfUserState()))
//            {
//                result.setLogicCode(LogicCodeConstants.USER_STATE);
//                return result;
//            }
            // 查询用户所能看到的模块
            List<MenuInfoBean> allFunInfoList = menuService.queryAllFunListByUserCode(userInfo.getfUserCode());
           
            if (allFunInfoList != null && allFunInfoList.size() > 0)
            {
                // 登陆后跳转主页面的菜单
                String mainMenuStr = formatMainMenu(allFunInfoList, request.getContextPath());
                if (mainMenuStr == null)
                {
                    mainMenuStr = "";
                }
              
                SessionUtil.setObjectAttribute(context.getRequest(), "userQXMenu", mainMenuStr);
            }

            // session中存储登录用户信息
            SessionUtil.setObjectAttribute(context.getRequest(), RequestConstants.ADMIN_SESSION_KEY, userInfo);
        
         
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.setSysCode(SystemCodeConstants.LOGIN_FAILED);
        }
        finally
        {
            long end = System.currentTimeMillis();
            logger.info("login check cost " + (end - begin) + " ms!");
        }

        return result;
    }
    

    /**
     * @Title: formatMainMenu
     * @Description: 根据登陆用户的权限信息组装主页面菜单
     * @param funInfoList
     * @return
     * @return String 返回类型
     * @throws
     */
    private String formatMainMenu(List<MenuInfoBean> funInfoList, String contextPath)
    {
        String retMsg = "<ul class='Lnav_list'>";
        for (MenuInfoBean funInfoObj : funInfoList)
        {
            // 级别为一级的，则为根菜单
            if (funInfoObj.getFuncType() == 0)
            {
                // 判断是否加载样式
                // 判断是否加载样式
                if (StringUtils.isNotBlank(funInfoObj.getFuncClass()))
                {
                    retMsg += "<li class='" + funInfoObj.getFuncClass() + "'";
                }
                else
                {
                    retMsg += "<li";
                }
                // 不是末级菜单，则有子节点
                if (funInfoObj.getMj() == 0)
                {
                    retMsg += ">" + funInfoObj.getFuncName() + "<div class='detailColumn'>";
                    for (MenuInfoBean funInfoObj_2 : funInfoList)
                    {
                        String parentId_1 = funInfoObj_2.getFuncParentId();
                        if (funInfoObj.getFuncId().equals(parentId_1))
                        {
                            // 不是末级菜单，则有子节点
                            if (funInfoObj_2.getMj() == 0)
                            {
                                retMsg += "<dl><dt>" + funInfoObj_2.getFuncName() + "</dt>";
                                for (MenuInfoBean funInfoObj_3 : funInfoList)
                                {
                                    String parentId_2 = funInfoObj_3.getFuncParentId();
                                    if (funInfoObj_2.getFuncId().equals(parentId_2))
                                    {
                                        retMsg += "<dd><a href=\"javascript:frameComponent.onChangMainWin('" + funInfoObj_3.getFuncName() + "', '"
                                                + contextPath + funInfoObj_3.getFuncURI() + "'); \">" + funInfoObj_3.getFuncName() + "</a></dd>";
                                    }
                                }

                                retMsg += "<br class='cb' /></dl>";

                            }
                            // 是末级菜单，则没有子节点
                            else
                            {
                                retMsg += "<dl><dt onclick =\" frameComponent.onChangMainWin('" + funInfoObj_2.getFuncName() + "', '" + contextPath
                                        + funInfoObj_2.getFuncURI() + "') \">" + funInfoObj_2.getFuncName() + "</dt></dl>";
                            }
                        }
                    }
                    retMsg += "</div></li> ";
                }
                // 是末级菜单，则没有子节点
                else
                {
                    retMsg += "  onclick=\"frameComponent.onChangMainWin('" + funInfoObj.getFuncName() + "', '" + contextPath
                            + funInfoObj.getFuncURI() + "');\">" + funInfoObj.getFuncName() + "</p> </li>";
                }
            }
        }
        retMsg += "</ul>";

        return retMsg;
    }

    /**
     * @Title: getLogout
     * @Description: 用户登出
     * @param context
     * @return
     * @return InvocationResult 返回类型
     * @throws
     */
    public InvocationResult getLogout(InvocationContext context)
    {
        // 返回结果对象
        InvocationResult result = InvocationResult.newInstance();
        HttpServletRequest request = context.getRequest();

        // 清空session中内容
        SessionUtil.removeObjectAttribute(request, RequestConstants.ADMIN_SESSION_KEY);
        result.setSysCode(SystemCodeConstants.LOGOUT_SYSTEM_INFO);
        return result;

    }


}
