package com.qm.omp.business.service.impl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.business.dao.admin.IUserDao;
import com.qm.omp.business.pojo.admin.UserInfoBean;
import com.qm.omp.business.service.iface.admin.ILoginService;
import com.qm.omp.business.web.aop.LogAnnotation;

/**
 * @ClassName: LoginServiceImpl
 * @Description:
 * @author rh
 * @date 2014-12-16 16:09:11
 */
@Service("loginService")
public class LoginServiceImpl implements ILoginService
{
    @Autowired
    private IUserDao userDao;

    @LogAnnotation(menuName = "登陆模块", method = "登陆", params = { "登录名" }, description = "登陆系统")
	@Override
    public UserInfoBean login(String loginName)
    {
        return userDao.selectByPrimaryKey(loginName);
    }

}
