package com.qm.omp.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.omp.api.cache.MemcachedKeyInfo;
import com.qm.omp.api.dao.MemcachedMapper;
import com.qm.omp.api.service.IMemcachedService;

/**
 * @ClassName: MemcachedServiceImpl
 * @Description: 
 * @author jihr
 * @date 2014-1-10 下午2:27:18
 */
@Service("memcachedService")
public class MemcachedServiceImpl implements IMemcachedService
{
    protected static final Logger logger = LoggerFactory.getLogger(MemcachedServiceImpl.class);

    @Autowired
    private MemcachedMapper memcachedMapper;

    @Override
    public List<MemcachedKeyInfo> queryMemKeyBySysNum(String sysNum)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sysNum", sysNum);
        return memcachedMapper.queryMemKeyList(paramMap);
    }
}