package com.yazuo.intelligent.mybatis.config;

import com.yazuo.intelligent.datasource.config.DataSourceAutoConfiguration;
import com.yazuo.intelligent.mybatis.MybatisMapperProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(MybatisMapperProperties.class)
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnBean(DataSourceAutoConfiguration.class)
public class MybatisAutoConfiguration  {
    @Autowired(required = false)
    private Interceptor[] interceptors = {};
    @Resource
    ApplicationContext context;
    @Resource
    MybatisMapperProperties mybatisMapperProperties;
    @PostConstruct
    public void checkConfigFileExists() {
        if (mybatisMapperProperties.isCheckConfigLocation()) {
            org.springframework.core.io.Resource resource = this.context
                    .getResource(mybatisMapperProperties.getConfig());
            Assert.state(resource.exists(),
                    "Cannot find config location: " + resource
                            + " (please add config file or check your Mybatis "
                            + "configuration)");
        }
    }

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        if(!StringUtils.isEmpty(mybatisMapperProperties.getConfig())){
            factory.setConfigLocation(context.getResource(mybatisMapperProperties.getConfig()));
        }else{
            if (this.interceptors.length > 0) {
                factory.setPlugins(this.interceptors);
            }
            factory.setTypeAliasesPackage(mybatisMapperProperties.getTypeAliasesPackage());
            factory.setTypeHandlersPackage(mybatisMapperProperties.getTypeHandlersPackage());
            factory.setMapperLocations(mybatisMapperProperties.getMapperLocations());
        }

        return factory.getObject();
    }
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory,
                mybatisMapperProperties.getExecutorType());
    }

}
