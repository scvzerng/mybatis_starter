package com.yazuo.intelligent.datasource.aop;

import com.yazuo.intelligent.datasource.dynamic.DataSourceContextHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * description
 * <p>
 * 2017-12-15 14:00
 *
 * @author scvzerng
 **/
@Aspect
public class DynamicDataSourceAspect {

    @Before("@annotation(dataSource)")
    public void changeDatasource(DataSource dataSource){
        DataSourceContextHolder.setDataSourceType(dataSource.vaule());
    }

    @After("@annotation(dataSource)")
    public void clearDatasource(DataSource dataSource){
        DataSourceContextHolder.clearDataSourceType();
    }
}
