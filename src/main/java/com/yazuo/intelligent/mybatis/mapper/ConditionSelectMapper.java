package com.yazuo.intelligent.mybatis.mapper;

import com.yazuo.intelligent.mybatis.mapper.condition.EntityCondition;
import com.yazuo.intelligent.mybatis.mapper.provider.ConditionSelectProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 *
 * Created by scvzerng on 2017/5/8.
 */
public interface ConditionSelectMapper<T> extends Mapper<T>,InsertListMapper<T> {
    /**
     * 按条件查询
     *
     * 支持动态条件
     * 支持排序
     * 支持动态列
     * @param condition
     * @return
     */
    @SelectProvider(type = ConditionSelectProvider.class, method = "dynamicSQL")
    List<T> selectByEntityCondition(@Param("entityCondition") EntityCondition condition);

}
