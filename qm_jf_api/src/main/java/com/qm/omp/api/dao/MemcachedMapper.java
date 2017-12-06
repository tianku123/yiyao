package com.qm.omp.api.dao;

import java.util.List;
import java.util.Map;

import com.qm.omp.api.cache.MemcachedKeyInfo;

/**
 * @ClassName: MemcachedMapper
 * @Description: 
 * @author jihr
 * @date 2014-1-10 下午2:01:25
 */
public interface MemcachedMapper
{
    /**
     * @Title: queryMemKeyList
     * @Description: 查询缓存key列表
     * @param map
     * @return List<MemcachedKeyInfo> 返回类型
     * @throws
     */
    public List<MemcachedKeyInfo> queryMemKeyList(Map<String, Object> map);
}
