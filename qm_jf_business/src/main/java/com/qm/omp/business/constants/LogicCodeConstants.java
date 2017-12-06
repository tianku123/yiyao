package com.qm.omp.business.constants;

import com.qm.omp.api.constants.ILogicCodeConstants;

public interface LogicCodeConstants extends ILogicCodeConstants
{
    // 登录失败原因
    String NOT_LOGIN               = "-10000101"; // 登录超时或尚未登录系统，请重新登录系统！
    String NOT_OPERATOR            = "-10000102"; // 您没权限执行该操作！
    String VERIFICATION_CODE_ERROR = "-10000103"; // 登录系统验证码不正确！请重新输入验证码！
    String LOGIN_PWD_EMPTY         = "-10000104"; // 登录帐号或登陆密码为空！请重新输入！
    String LOGIN_PWD_ERROR         = "-10000105"; // 登录密码不正确！请重新输入！
    String NOT_USER                = "-10000106"; // 登录帐号不存在！请重新输入！
    String USER_STATE              = "-10000107"; // 用户状态无效！
    String SHOP_STATE              = "-10000108"; // 该用户所在门店不可用！
    String MER_STATE               = "-10000109"; // 该用户所在商户不可用！
    String NOT_MERCHANT            = "-10000110"; // 商户不存在！

}