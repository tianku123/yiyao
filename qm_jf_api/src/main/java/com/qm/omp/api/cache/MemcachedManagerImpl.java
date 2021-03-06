package com.qm.omp.api.cache;

import java.util.Date;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @ClassName: MemcachedManagerImpl
 * @Description: 缓存管理实现类
 * @author jihr
 * @date 2013-9-29 上午9:34:24
 */
public class MemcachedManagerImpl implements IMemcachedManager
{
    private static final Logger logger          = LoggerFactory.getLogger(MemcachedManagerImpl.class);

    private MemCachedClient     memCachedClient = null;

    String                      poolName;

    String                      servers;

    String                      weights;

    String                      initConn;

    String                      minConn;

    String                      maxConn;

    String                      maxIdle;

    String                      maintSleep;

    String                      nagle;

    String                      socketTo;

    String                      socketConnectTo;

    public String getPoolName()
    {
        return poolName;
    }

    public void setPoolName(String poolName)
    {
        this.poolName = poolName;
    }

    public String getServers()
    {
        return servers;
    }

    public void setServers(String servers)
    {
        this.servers = servers;
    }

    public String getWeights()
    {
        return weights;
    }

    public void setWeights(String weights)
    {
        this.weights = weights;
    }

    public String getInitConn()
    {
        return initConn;
    }

    public void setInitConn(String initConn)
    {
        this.initConn = initConn;
    }

    public String getMinConn()
    {
        return minConn;
    }

    public void setMinConn(String minConn)
    {
        this.minConn = minConn;
    }

    public String getMaxConn()
    {
        return maxConn;
    }

    public void setMaxConn(String maxConn)
    {
        this.maxConn = maxConn;
    }

    public String getMaxIdle()
    {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle)
    {
        this.maxIdle = maxIdle;
    }

    public String getMaintSleep()
    {
        return maintSleep;
    }

    public void setMaintSleep(String maintSleep)
    {
        this.maintSleep = maintSleep;
    }

    public String getNagle()
    {
        return nagle;
    }

    public void setNagle(String nagle)
    {
        this.nagle = nagle;
    }

    public String getSocketTo()
    {
        return socketTo;
    }

    public void setSocketTo(String socketTo)
    {
        this.socketTo = socketTo;
    }

    public String getSocketConnectTo()
    {
        return socketConnectTo;
    }

    public void setSocketConnectTo(String socketConnectTo)
    {
        this.socketConnectTo = socketConnectTo;
    }

    public MemCachedClient getMemCachedClient()
    {
        return memCachedClient;
    }

    public void setMemCachedClient(MemCachedClient memCachedClient)
    {
        this.memCachedClient = memCachedClient;
    }

    /**
     * 销毁函数
     */
    public void destory()
    {
        SockIOPool pool = SockIOPool.getInstance(this.poolName);
        pool.shutDown();
        this.memCachedClient = null;
    }

    /**
     * 初始化方法，bean构造后自动加载
     * 
     * @throws Exception
     */
    public void initialize() throws Exception
    {
        try
        {
            logger.info("初始化Memcached客户端...");

            memCachedClient = new MemCachedClient(this.poolName);
            // 开始验证...
            // 缓存服务器和其权重是否配置验证
            if (servers == null || servers.trim().equals(""))
            {
                logger.error("服务器列表配置不能为空......缓存建立失败！");
            }
            if (weights == null || weights.trim().equals(""))
            {
                logger.error("服务器的权重配置不能为空......缓存建立失败！");
            }

            // 服务器列表和其权重
            String[] servers = this.servers.split(",");
            String[] strWeights = weights.split(",");
            Integer[] weights = new Integer[strWeights.length];
            for (int i = 0; i < strWeights.length; i++)
            {
                weights[i] = Integer.parseInt(strWeights[i]);
            }

            // 缓存服务器和其权重配置个数是否相等验证
            if (servers.length != weights.length)
            {
                logger.error("服务器和其权重配置个数不相等......缓存建立失败！");
            }

            // 初始连接数、最小和最大连接数以及最大处理时间验证
            if (initConn != null && !Pattern.compile("[0-9]*").matcher(initConn).matches())
            {
                logger.error("初始连接数配置不能为字符......加载失败！");
            }
            if (minConn != null && !Pattern.compile("[0-9]*").matcher(minConn).matches())
            {
                logger.error("最小连接数配置不能为字符......加载失败！");
            }
            if (maxConn != null && !Pattern.compile("[0-9]*").matcher(maxConn).matches())
            {
                logger.error("最大连接数配置不能为字符......加载失败！");
            }
            if (maxIdle != null && !Pattern.compile("[0-9]*").matcher(maxIdle).matches())
            {
                logger.error("最大处理时间配置不能为字符......加载失败！");
            }

            // 主线程的睡眠时间验证
            if (maintSleep != null && !Pattern.compile("[0-9]*").matcher(maintSleep).matches())
            {
                logger.error("主线程睡眠时间配置不能为字符......加载失败！");
            }

            // TCP的参数，连接超时验证
            if (nagle != null && !nagle.trim().equals("") && !nagle.trim().equals("false") && !nagle.trim().equals("true"))
            {
                logger.error("TCP参数nagle配置只能为空值或者true或者false......加载失败！");
            }
            if (socketTo != null && !Pattern.compile("[0-9]*").matcher(socketTo).matches())
            {
                logger.error("TCP参数socketTo配置不能为字符......加载失败！");
            }
            if (socketConnectTo != null && !Pattern.compile("[0-9]*").matcher(socketConnectTo).matches())
            {
                logger.error("TCP参数socketConnectTo配置不能为字符......加载失败！");
            }

            // 获取socke连接池的实例对象
            SockIOPool pool = SockIOPool.getInstance(this.poolName);
            // 设置服务器信息
            pool.setServers(servers);
            pool.setWeights(weights);

            // 设置初始连接数、最小和最大连接数以及最大处理时间
            if (initConn != null && !initConn.trim().equals(""))
            {
                pool.setInitConn(Integer.parseInt(initConn));
            }
            if (minConn != null && !minConn.trim().equals(""))
            {
                pool.setMinConn(Integer.parseInt(minConn));
            }
            if (maxConn != null && !maxConn.trim().equals(""))
            {
                pool.setMaxConn(Integer.parseInt(maxConn));
            }
            if (maxIdle != null && !maxIdle.trim().equals(""))
            {
                pool.setMaxIdle(Integer.parseInt(maxIdle));
            }

            // 设置主线程的睡眠时间
            if (maintSleep != null && !maintSleep.trim().equals(""))
            {
                pool.setMaintSleep(Long.parseLong(maintSleep));
            }

            // 设置TCP的参数，连接超时等
            if (nagle != null && !nagle.trim().equals("") && (nagle.trim().equals("false") || nagle.trim().equals("true")))
            {
                pool.setNagle(Boolean.parseBoolean(nagle));
            }
            if (socketTo != null && !socketTo.trim().equals(""))
            {
                pool.setSocketTO(Integer.parseInt(socketTo));
            }
            if (socketConnectTo != null && !socketConnectTo.trim().equals(""))
            {
                pool.setSocketConnectTO(Integer.parseInt(socketConnectTo));
            }

            // 初始化连接池
            pool.initialize();
        }
        catch (Exception e)
        {
            logger.info("客户端初始失败");
            throw e;
        }
        logger.info("Memcached客户端初始化成功!");
    }

    public boolean add(String key, Object value, long expireInMilliSeconds)
    {
        Date d = new Date(expireInMilliSeconds);
        return this.memCachedClient.add(key, value, d);
    }

    public boolean add(String key, Object value)
    {

        return this.memCachedClient.add(key, value);
    }

    public boolean delete(String key)
    {
        return this.memCachedClient.delete(key);
    }

    public boolean replace(String key, Object value)
    {
        return this.memCachedClient.replace(key, value);
    }

    public boolean replace(String key, Object value, long expireInMilliSeconds)
    {
        Date d = new Date(expireInMilliSeconds);
        return this.memCachedClient.replace(key, value, d);
    }

    public Object get(String key)
    {
        return this.memCachedClient.get(key);
    }
}
