package com.yazuo.intelligent.mybatis;

import lombok.Data;
import org.apache.ibatis.session.Configuration;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.yazuo.mapper.ConditionRemoveMapper;
import org.yazuo.mapper.ConditionSelectMapper;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Config;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/10/17-20:35
 * Project:intelligent_starter_parent
 * Package:com.yazuo.intelligent.mybatis.config
 * To change this template use File | Settings | File Templates.
 */
@Data
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
    private List<Class> mappers = Arrays.asList(ConditionRemoveMapper.class,Mapper.class, ConditionSelectMapper.class);
    /**
     * mybatis配置
     */
    @NestedConfigurationProperty
    private Configuration configuration;

}
