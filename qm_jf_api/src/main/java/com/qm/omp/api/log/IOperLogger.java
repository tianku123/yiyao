package com.qm.omp.api.log;

import com.qm.omp.api.invocation.InvocationContext;

public interface IOperLogger
{
    /**
     * @Title: writeLog
     * @Description: 记录操作日志
     * @param invoName
     * @param methodName
     * @param context
     * @param ret
     * @return void 返回类型
     * @throws
     */
    public void writeLog(String invoName, String methodName, InvocationContext context, boolean ret);
}
