package com.zero.scvzerng.mybatis.mapper;

import com.MapperLocationClass;
import com.zero.scvzerng.datasource.config.DataSourceAutoConfiguration;
import com.zero.scvzerng.mybatis.config.MybatisAutoConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@ConditionalOnClass(MapperScannerConfigurer.class)
@ConditionalOnBean(DataSourceAutoConfiguration.class    )
@MapperScan(basePackageClasses = MapperLocationClass.class,annotationClass = Mapper.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MapperScanAutoConfiguration {
}
