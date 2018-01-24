package com.zero.scvzerng.mybatis.config;

import com.zero.scvzerng.datasource.config.DataSourceAutoConfiguration;
import com.zero.scvzerng.mybatis.MybatisMapperProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@EnableConfigurationProperties(MybatisMapperProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@ConditionalOnBean(DataSourceAutoConfiguration.class)
public class MapperAutoConfiguration {
    @Resource
    ApplicationContext context;
    @Resource
    List<SqlSessionFactory> sqlSessionFactoryList;
    @Resource
    MybatisMapperProperties mybatisMapperProperties;
    @PostConstruct
    public void init() {
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(mybatisMapperProperties);
        mybatisMapperProperties.getMappers().forEach(mapper->{
            context.getBeansOfType(mapper);
            mapperHelper.registerMapper(mapper);
        });

        sqlSessionFactoryList.stream()
                .map(SqlSessionFactory::getConfiguration)
                .forEach(mapperHelper::processConfiguration);

    }


}
