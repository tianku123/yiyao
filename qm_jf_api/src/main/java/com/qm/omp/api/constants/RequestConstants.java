package com.qm.omp.api.constants;

public interface RequestConstants
{
    String ADMIN_CHECK_CODE     = "admin_login_code";    // 管理员登陆的验证码

    String ADMIN_SESSION_KEY    = "admin";               // 管理员存储session 的对象Key

    String RESULT_INFO          = "resultInfo";          // 信息页面结果对象
    String INFORMATION          = "information";         // 信息页面需要的信息对象
    String CUS_MAIL_OR_SMS_CODE = "cus_mail_or_sms_code"; // 会员短信或邮箱发送的验证码
}
