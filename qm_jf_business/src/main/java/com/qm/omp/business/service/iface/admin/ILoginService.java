package com.qm.omp.business.service.iface.admin;

import com.qm.omp.business.pojo.admin.UserInfoBean;

/**
 * @ClassName: ILoginService
 * @Description:
 * @author rh
 * @date 2014-11-28 10:24:04
 */
public interface ILoginService
{
    /**
     * @Title: login
     * @Description: 根据用户名查询用户信息
     * @param loginName
     * @return
     * @return UserInfoBean 返回类型
     * @throws
     */
    public UserInfoBean login(String loginName);

}
