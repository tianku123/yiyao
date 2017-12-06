package com.qm.omp.api.service;

import java.util.List;

import com.qm.omp.api.cache.MemcachedKeyInfo;

public interface IMemcachedService
{
    /**
     * @Title: queryMemKeyBySysNum
     * @Description: 查询缓存key列表
     * @param sysNum
     * @return String 返回类型
     * @throws
     */
    public List<MemcachedKeyInfo> queryMemKeyBySysNum(String sysNum);
}
