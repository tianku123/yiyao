package com.qm.omp.api.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: DataSources
 * @Description: 数据源管理器
 * @author jihr
 * @date 2014-1-10 下午5:55:51
 */
public class RoutingDataSourceStrategy extends AbstractRoutingDataSource
{
    @Override
    protected Object determineCurrentLookupKey()
    {
        return RoutingContext.getName();
    }

}
