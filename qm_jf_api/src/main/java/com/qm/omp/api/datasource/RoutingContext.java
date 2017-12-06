package com.qm.omp.api.datasource;

/**
 * @ClassName: RoutingContext
 * @Description: DB路由环境
 * @author jihr
 * @date 2014-1-10 下午5:54:29
 */
public class RoutingContext
{
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    
    public static final String READ_PREFIX = "rd";
    
    public static final String WRITE_PREFIX = "wt";
    
    public static final String READ_DATASOURCE = "read";
    
    public static final String WRITE_DATASOURCE = "write";
    
    public static void setName(String name)
    {
        contextHolder.set(name);
    }
    
    public static String getName()
    {
        return contextHolder.get();
    }
    
    public static void clearName()
    {
        contextHolder.remove();
    }

}
