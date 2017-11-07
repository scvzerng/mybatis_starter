package com.yazuo.intelligent.mybatis.mapper;

import com.yazuo.intelligent.MapperLocationClass;
import com.yazuo.intelligent.datasource.config.DataSourceAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@ConditionalOnClass(MapperScannerConfigurer.class)
@ConditionalOnBean(DataSourceAutoConfiguration.class    )
@MapperScan(basePackageClasses = MapperLocationClass.class,markerInterface = Mapper.class)
public class MapperScanAutoConfiguration {
}
