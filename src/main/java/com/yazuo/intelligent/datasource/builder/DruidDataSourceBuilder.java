package com.yazuo.intelligent.datasource.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.yazuo.intelligent.datasource.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *
 * Druid 构建器
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/10/22-16:44
 * Project:intelligent_starter_parent
 * Package:com.yazuo.intelligent.datasource.builder
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class DruidDataSourceBuilder implements DataSourceBuilder {
    @Override
    public DataSource buildDataSource(DataSourceConfig dataSourceConfig) {
        log.info("init datasource \n {}", dataSourceConfig);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceConfig.getUrl());
        dataSource.setUsername(dataSourceConfig.getUsername());
        dataSource.setPassword(dataSourceConfig.getPassword());
        dataSource.setInitialSize(dataSourceConfig.getInitialSize());
        dataSource.setMinIdle(dataSourceConfig.getMinIdle());
        dataSource.setMaxActive(dataSourceConfig.getMaxActive());
        dataSource.setMaxWait(dataSourceConfig.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(dataSourceConfig.getValidationQuery());
        dataSource.setTestWhileIdle(dataSourceConfig.getTestWhileIdle());
        dataSource.setTestOnBorrow(dataSourceConfig.getTestOnBorrow());
        dataSource.setTestOnReturn(dataSourceConfig.getTestOnReturn());
        dataSource.setDefaultAutoCommit(dataSourceConfig.getDefaultAutoCommit());
        dataSource.setRemoveAbandoned(dataSourceConfig.getRemoveAbandoned());
        dataSource.setRemoveAbandonedTimeout(dataSourceConfig.getRemoveAbandonedTimeout());
        dataSource.setNumTestsPerEvictionRun(dataSourceConfig.getNumTestsPerEvictionRun());
        dataSource.setPoolPreparedStatements(dataSourceConfig.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            dataSource.setFilters(dataSourceConfig.getFilters());
        } catch (SQLException e) {
            log.warn("set druid datasource filter fail filters[{}]",dataSourceConfig.getFilters());
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
