package com.qm.omp.api.datasource;

import org.aspectj.lang.JoinPoint;

/**
 * @ClassName: MemcachedAdviseExecutor
 * @Description: DAO数据缓存AOP
 * @author jihr
 * @date 2014-1-10 上午11:36:07
 */
public class DataSourceAdviseExecutor
{
    /**
     * @Title: transmit
     * @Description:  拦截需要读写分离的DAO方法
     * @param pjp
     * @return void 返回类型
     * @throws
     */
    public void transmit(JoinPoint point)
    {
        // 方法名
        String methodName = point.getSignature().getName();

        if (methodName.startsWith(RoutingContext.WRITE_PREFIX))
        {
            RoutingContext.setName(RoutingContext.WRITE_DATASOURCE);
        }
        else if (methodName.startsWith(RoutingContext.READ_PREFIX))
        {
            RoutingContext.setName(RoutingContext.READ_DATASOURCE);
        }
    }
}
