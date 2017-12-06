package com.qm.omp.api.cache;

public class MemcachedKeyInfo
{
    // key值
    private String  memKey;
    // 系统编码
    private String  sysNum;
    // 缓存名
    private String  cacheName;
    // 对应的DAO
    private String  daoName;
    // 对应的方法
    private String  method;
    // 是否需要替换参数 如：****{0}****{1}****
    private boolean needParam;
    // 缓存时长
    private long    expireInSeconds;

    public String getDaoName()
    {
        return daoName;
    }

    public void setDaoName(String daoName)
    {
        this.daoName = daoName;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public long getExpireInSeconds()
    {
        return expireInSeconds;
    }

    public void setExpireInSeconds(long expireInSeconds)
    {
        this.expireInSeconds = expireInSeconds;
    }

    public boolean isNeedParam()
    {
        return needParam;
    }

    public void setNeedParam(boolean needParam)
    {
        this.needParam = needParam;
    }

    public String getMemKey()
    {
        return memKey;
    }

    public void setMemKey(String memKey)
    {
        this.memKey = memKey;
    }

    public String getCacheName()
    {
        return cacheName;
    }

    public void setCacheName(String cacheName)
    {
        this.cacheName = cacheName;
    }

    public String getSysNum()
    {
        return sysNum;
    }

    public void setSysNum(String sysNum)
    {
        this.sysNum = sysNum;
    }
}
