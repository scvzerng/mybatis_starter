package com.yazuo.intelligent.datasource.aop;

import java.lang.annotation.*;

/**
 * description
 * <p>
 * 2017-12-15 13:54
 *
 * @author scvzerng
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String vaule();
}
