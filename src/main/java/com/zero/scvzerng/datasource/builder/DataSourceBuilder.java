package com.zero.scvzerng.datasource.builder;

import com.zero.scvzerng.datasource.config.DataSourceConfig;

import javax.sql.DataSource;

/**
 *
 * 数据源构建
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/10/22-16:42
 * Project:intelligent_starter_parent
 * Package:com.yazuo.intelligent.datasource
 * To change this template use File | Settings | File Templates.
 */

public interface DataSourceBuilder {
    DataSource buildDataSource(DataSourceConfig dataSourceConfig);
}
