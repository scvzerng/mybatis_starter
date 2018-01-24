package com.zero.scvzerng.mybatis.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import com.zero.scvzerng.datasource.config.DataSourceAutoConfiguration;
import com.zero.scvzerng.mybatis.PageHelperProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableConfigurationProperties(PageHelperProperties.class)
@ConditionalOnClass({PageHelper.class,SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnBean(DataSourceAutoConfiguration.class)
public class PageHelperAutoConfiguration {
    @Resource
    List<SqlSessionFactory> sqlSessionFactoryList;
    @Resource
    private PageHelperProperties pageHelperProperties;
    @PostConstruct
     public void addPageInterceptor(){
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(pageHelperProperties.getProps());
        sqlSessionFactoryList.stream().map(SqlSessionFactory::getConfiguration)
                .forEach(configuration -> configuration.addInterceptor(interceptor));
    }



}
