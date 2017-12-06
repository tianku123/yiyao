package com.qm.omp.api.cache;

import java.text.MessageFormat;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qm.omp.api.web.BaseWebProperties;

/**
 * @ClassName: MemcachedAdviseExecutor
 * @Description: DAO数据缓存AOP
 * @author jihr
 * @date 2014-1-10 上午11:36:07
 */
public class MemcachedAdviseExecutor
{
    private static Logger       logger          = LoggerFactory.getLogger(MemcachedAdviseExecutor.class);
    private final static Logger countTimeLogger = LoggerFactory.getLogger("countTimeLogger");

    /**
     * @throws Exception
     * @Title: getMemData
     * @Description: 通过AOP方式实现DB数据缓存的功能
     * @param pjp
     * @return
     * @return Object 返回类型
     * @throws
     */
    public Object getMemData(ProceedingJoinPoint pjp) throws Exception
    {
        // 代理对象
        String target = pjp.getSignature().getDeclaringTypeName();
        // 方法名
        String methodName = pjp.getSignature().getName();
        // 方法参数
        Object[] args = pjp.getArgs();
        Object ret = null;
        if (!methodName.endsWith("4Mem"))
        {
            long startTime = System.currentTimeMillis();
            try
            {
                ret = pjp.proceed();
            }
            catch (Throwable e)
            {
                logger.error("执行" + target + "的" + methodName + "发生错误，", e);
                throw new Exception(e);
            }
            finally
            {
                long endTime = System.currentTimeMillis();
                countTimeLogger.info(target + "的" + methodName + "执行花费了" + (endTime - startTime) + "毫秒");
            }
        }
        else
        {
            String key = target + "_" + methodName;
            MemcachedKeyInfo memcachedKey = (MemcachedKeyInfo) BaseWebProperties.MEMCACHE_KEY_MAP.get(key);
            if (memcachedKey == null)
            {
                logger.error("请检查方法" + target + "-" + methodName + "的memcached key信息是否配置......");
                return null;
            }
            String finalMemKey = memcachedKey.getMemKey();
            if ((args != null && args.length > 0) && memcachedKey.isNeedParam())
            {
                MessageFormat msgFormat = new MessageFormat(finalMemKey);
                finalMemKey = msgFormat.format(this.parseArgs(args));
            }
            // 增加系统编码区别相同名称的key
            finalMemKey = BaseWebProperties.SYS_NUM + "_" + finalMemKey;
            IMemcachedManager cache = (IMemcachedManager) BaseWebProperties.SPRING_CONTEXT.getBean(memcachedKey.getCacheName());
            ret = cache.get(finalMemKey);
            if (ret == null)
            {
                try
                {
                    ret = pjp.proceed();
                    logger.info(finalMemKey);
                    cache.add(finalMemKey, ret, memcachedKey.getExpireInSeconds());
                }
                catch (Throwable e)
                {
                    throw new Exception(e);
                }
            }
        }

        return ret;
    }

    /**
     * @Title: parseArgs
     * @Description: 解析参数
     * @param args
     * @return
     * @return Object[] 返回类型
     * @throws
     */
    @SuppressWarnings({"unchecked"})
    private Object[] parseArgs(Object[] args)
    {
        Object firstArg = args[0];
        if (firstArg instanceof Map)
        {
            return ((Map<String, Object>) firstArg).values().toArray();
        }

        return args;
    }
}
