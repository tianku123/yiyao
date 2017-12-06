package com.qm.omp.api.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.qm.omp.api.cache.MemcachedKeyInfo;

public class BaseWebProperties
{
    public static String                        SYS_NUM             = "";                                     // 系统编码
    public static String                        CONTEXT_NAME        = "";                                     // 上下文环境名称
    public static String                        BUSINESS_FILE_ROOT  = "";                                     // 文件上传根目录
    public static String                        BUSINESS_FILE_UPDIR = "";                                     // 文件上传目录（除去根目录）
    public static ApplicationContext            SPRING_CONTEXT      = null;                                   // Spring上下文
    public static Map<String, MemcachedKeyInfo> MEMCACHE_KEY_MAP    = new HashMap<String, MemcachedKeyInfo>(); // 缓存存放的Map
}
