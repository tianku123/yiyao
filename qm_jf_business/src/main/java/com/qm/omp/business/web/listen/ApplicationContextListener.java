package com.qm.omp.business.web.listen;

import java.net.URL;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qm.common.util.ConfigurationRead;
import com.qm.omp.api.cache.MemcachedKeyInfo;
import com.qm.omp.api.constants.ISystemConstants;
import com.qm.omp.api.service.IMemcachedService;
import com.qm.omp.api.web.BaseWebProperties;

/**
 * @ClassName: ApplicationContextListener
 * @Description:
 * @author jhr
 * @date 2014-1-8 下午4:19:30
 */
public class ApplicationContextListener implements ServletContextListener
{
    private Logger logger = Logger.getLogger(ApplicationContextListener.class);
    private Timer  timer  = new Timer();
    static
    {
    }

    public void contextDestroyed(ServletContextEvent arg0)
    {
    }

    public void contextInitialized(ServletContextEvent context)
    {
    	
        logger.info(".........初始化系统参数开始..................");
        String realPath = context.getServletContext().getRealPath("");
        if (!realPath.endsWith("/"))
        {
            realPath += "/";
        }

        // 手动加载log4j
        URL url = this.getClass().getClassLoader().getResource("log4j.properties");
        PropertyConfigurator.configure(url);

        BaseWebProperties.CONTEXT_NAME = context.getServletContext().getServletContextName();
        BaseWebProperties.SPRING_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
        ConfigurationRead read = new ConfigurationRead("/", "shopCenter_business.properties");
        // 当前系统编码
        BaseWebProperties.SYS_NUM = read.getValue("sys.num");
        // 文件上传根目录
        BaseWebProperties.BUSINESS_FILE_ROOT = read.getValue("business.file.upload.root");
        // 文件上传目录（除去根目录）
        BaseWebProperties.BUSINESS_FILE_UPDIR = read.getValue("business.file.upload.dir");

        // 加载缓存key列表到内存
        initMemcachedKeyList();

        logger.info("..........初始化系统参数结束..................");
    }

    /**
     * @Title: initMemcachedKeyList
     * @Description: 加载缓存key列表到内存
     * @return void 返回类型
     * @throws
     */
    private void initMemcachedKeyList()
    {
        IMemcachedService memcachedService = (IMemcachedService) BaseWebProperties.SPRING_CONTEXT.getBean("memcachedService");
        List<MemcachedKeyInfo> keyList = memcachedService.queryMemKeyBySysNum(BaseWebProperties.SYS_NUM);
        if (keyList != null && keyList.size() > 0)
        {
            for (int i = 0; i < keyList.size(); i++)
            {
                MemcachedKeyInfo keyInfo = (MemcachedKeyInfo) keyList.get(i);
                if (keyInfo != null && keyInfo.getExpireInSeconds() == 0)
                {
                    keyInfo.setExpireInSeconds(ISystemConstants.MEMCACHED_DEFAULT_EXPIRE);
                }
                BaseWebProperties.MEMCACHE_KEY_MAP.put(keyInfo.getDaoName() + "_" + keyInfo.getMethod(), keyInfo);
            }
        }
    }
}
