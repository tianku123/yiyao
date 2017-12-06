package com.qm.omp.api.log;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.qm.omp.api.invocation.InvocationContext;
import com.qm.omp.api.invocation.InvocationResult;

/**
 * @ClassName: OperLogAdviseExecutor
 * @Description: 记录系统操作日志AOP
 * @author jihr
 * @date 2014-4-18 下午3:21:46
 */
public class OperLogAdviseExecutor
{
    @Autowired
    private IOperLogger operLogger;

    /**
     * @Title: doInvoAfterReturnLog
     * @Description: 记录操作日志, 正常返回
     * @param pjp
     * @param result
     * @return void 返回类型
     * @throws
     */
    public void doInvoAfterReturnLog(JoinPoint pjp, Object result)
    {
        // 方法名
        String methodName = pjp.getSignature().getName();
        // invo name
        String invoName = pjp.getTarget().getClass().getSimpleName();
        // 方法参数
        Object[] args = pjp.getArgs();
        InvocationContext context = (InvocationContext) args[0];
        InvocationResult invoRet = (InvocationResult) result;
        operLogger.writeLog(invoName, methodName, context, invoRet.isSuccess());
    }

    /**
     * @Title: doInvoAfterThrowLog
     * @Description: 记录操作日志, 发生异常
     * @param pjp
     * @param e
     * @return void 返回类型
     * @throws
     */
    public void doInvoAfterThrowLog(JoinPoint pjp, IllegalArgumentException ex)
    {
        // 方法名
        String methodName = pjp.getSignature().getName();
        // invo name
        String invoName = pjp.getTarget().getClass().getSimpleName();
        // 方法参数
        Object[] args = pjp.getArgs();
        InvocationContext context = (InvocationContext) args[0];
        operLogger.writeLog(invoName, methodName, context, false);
    }
}
