package com.yazuo.intelligent.datasource.config;
import com.yazuo.intelligent.datasource.aop.DynamicDataSourceAspect;
import com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.datasource.DataSourceProperties;
import com.yazuo.intelligent.datasource.builder.DataSourceBuilder;
import com.yazuo.intelligent.datasource.builder.DruidDataSourceBuilder;
import com.yazuo.intelligent.datasource.dynamic.MultipleDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Configuration
@ConditionalOnExpression("${intelligent.datasource.enable:true}")
@EnableTransactionManagement
@EnableConfigurationProperties(DataSourceProperties.class)
@ConditionalOnClass(value = {SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class DataSourceAutoConfiguration {

    /**
     * 默认使用Druid构建器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceBuilder dataSourceBuilder(){
        return new DruidDataSourceBuilder();
    }

    @Resource
    private DataSourceProperties dataSources;
    @Bean
    @ConditionalOnMissingBean(DataSourceTransactionManager.class)
    public PlatformTransactionManager platformTransactionManager(MultipleDataSource multipleDataSource){
        DataSourceTransactionManager platformTransactionManager = new DataSourceTransactionManager();
        platformTransactionManager.setDataSource(multipleDataSource);
        return platformTransactionManager;
    }
    @Bean
    @ConditionalOnMissingBean
    public MultipleDataSource multipleDataSource(DataSourceBuilder dataSourceBuilder) {
        log.info("init multi datasource");
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        log.info("set master datasource = {}",dataSources.getMaster());
        multipleDataSource.setMaster(dataSources.getMaster());
        multipleDataSource.setDefaultTargetDataSource(getDefault(dataSourceBuilder));
        Map<Object,Object> targets = getTargets(dataSourceBuilder);
        if(targets!=null){
            log.info("set target datasource = {}",JSON.toJSONString(targets.keySet()));
            multipleDataSource.setTargetDataSources(targets);
        }
        log.info("multi datasource init success");
        return multipleDataSource;
    }



    public Object getDefault(DataSourceBuilder dataSourceBuilder){
        DataSourceConfig dataSourceConfig = null;
        if(dataSources.getDataSources()!=null){
            dataSourceConfig = dataSources.getDataSources().get(dataSources.getMaster());
            if(dataSourceConfig==null){
                if(dataSources.getDataSources().values().size()==1){
                    dataSourceConfig = (DataSourceConfig) dataSources.getDataSources().values().toArray()[0];
                }
            }

        }
        if(dataSourceConfig==null){
            throw new NullPointerException("must be set a datasource");
        }

        return dataSourceBuilder.buildDataSource(dataSourceConfig);
    }

    public Map<Object,Object> getTargets(DataSourceBuilder dataSourceBuilder){
        Map<Object,Object> targets = null;
        Object defaultDatasource = getDefault(dataSourceBuilder);
        if(dataSources.getDataSources()!=null) {
            targets =  dataSources.getDataSources().keySet().stream().filter(key->!dataSources.getMaster().equals(key)).collect(HashMap::new, (db, key) -> db.put(key, dataSourceBuilder.buildDataSource(dataSources.getDataSources().get(key))), HashMap::putAll);
        }
        if(targets==null&&defaultDatasource==null) throw new IllegalStateException("没有发现任何数据源");
        return targets;
    }

    @Bean
    public DynamicDataSourceAspect dataSourceAspect(){
        return new DynamicDataSourceAspect();
    }
}
