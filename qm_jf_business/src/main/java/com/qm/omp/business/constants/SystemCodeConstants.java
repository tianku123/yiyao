package com.qm.omp.business.constants;

import com.qm.omp.api.constants.ISystemCodeConstants;

public interface SystemCodeConstants extends ISystemCodeConstants
{
    /**** 成功编码 以1开头 6位 如：100001 *****/
    String LOGOUT_SYSTEM_INFO          = "100001"; // 您已经安全退出系统！谢谢使用！

    String UPDATE_PASSWORD_SUCCESS     = "100002"; // 修改登录密码成功！

    String MODIFY_QX_SUCCESS           = "100003"; // 修改权限成功，重新登后修改生效。

    /**** 错误编码 以-1开头 6位 如：-100001 *****/
    String LOGIN_FAILED                = "-100001"; // 登陆失败！请稍候再试！

    String ADMIN_TOKEN_FAILED          = "-100002"; // 用户鉴权失败

    String MODIFY_USER_PASSWORD_FAILED = "-100003"; // 密码修改失败
}
