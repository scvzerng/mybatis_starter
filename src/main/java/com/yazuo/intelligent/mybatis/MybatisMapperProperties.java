package com.yazuo.intelligent.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Config;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/10/17-20:35
 * Project:intelligent_starter_parent
 * Package:com.yazuo.intelligent.mybatis.config
 * To change this template use File | Settings | File Templates.
 */
@ConfigurationProperties(MybatisMapperProperties.PREFIX)
public class MybatisMapperProperties extends Config {
    public static final String PREFIX = "mybatis";
    /**
     *  MyBatis 的 XML 配置文件路径
     */
    private String config;
    /**
     * Mapper XML文件所在位置
     */
    private Resource[] mapperLocations ;
    /**
     * 扫描类型别名所在包
     */
    private String typeAliasesPackage ;
    /**
     * 扫描类型处理器所在包
     */
    private String typeHandlersPackage = "com.yazuo";
    /**
     * 是否检查 mybatis XML配置
     */
    private boolean checkConfigLocation = false;
    /**
     * SQL执行器
     */
    private ExecutorType executorType = ExecutorType.SIMPLE;
    /**
     *需要注册的Mapper
     */
    private List<Class> mappers = Collections.singletonList(Mapper.class);

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Resource[] getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getTypeHandlersPackage() {
        return typeHandlersPackage;
    }

    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public boolean isCheckConfigLocation() {
        return checkConfigLocation;
    }

    public void setCheckConfigLocation(boolean checkConfigLocation) {
        this.checkConfigLocation = checkConfigLocation;
    }

    public ExecutorType getExecutorType() {
        return executorType;
    }

    public void setExecutorType(ExecutorType executorType) {
        this.executorType = executorType;
    }

    public List<Class> getMappers() {
        return mappers;
    }

    public void setMappers(List<Class> mappers) {
        this.mappers = mappers;
    }
}
