package com.qm.omp.api.constants;

public interface ISystemConstants
{
    long   MEMCACHED_DEFAULT_EXPIRE = 7 * 24 * 60 * 60 * 1000; // memcached默认失效时间为：7天

    String CHARSET_NAME             = "UTF-8";

    String SYS_SUCCESS              = "0";                     // 系统处理成功
    String SYS_FAILED               = "1";                     // 系统处理失败
    String INVOCATION_FAILED        = "2";                     // 请求处理失败

}
