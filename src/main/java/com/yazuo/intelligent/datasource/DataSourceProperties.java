package com.yazuo.intelligent.datasource;

import com.yazuo.intelligent.datasource.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/10/17-20:58
 * Project:intelligent_starter_parent
 * Package:com.yazuo.intelligent.datasource.config
 * To change this template use File | Settings | File Templates.
 */
@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
@Slf4j
public class DataSourceProperties  {
    public static final String PREFIX = "intelligent.datasource";
    private Map<String,DataSourceConfig> dataSources;
    public static final String DEFAULT = "defaultDataSource";
    /**
     * 默认数据源
     */
    private String master = DEFAULT;
    /**
     * 启用数据源
     */
    public boolean enable = false;

    public Map<String, DataSourceConfig> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, DataSourceConfig> dataSources) {
        this.dataSources = dataSources;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }



}
