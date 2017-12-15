package org.yazuo.mapper;

import com.yazuo.intelligent.mybatis.mapper.condition.EntityCondition;
import com.yazuo.intelligent.mybatis.mapper.provider.ConditionRemoveProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 *
 * Created by scvzerng on 2017/5/8.
 */
public interface ConditionRemoveMapper<T> extends Mapper<T>,InsertListMapper<T> {
    /**
     * 按条件查询
     *
     * 支持动态条件
     * @param condition
     * @return
     */
    @DeleteProvider(type = ConditionRemoveProvider.class, method = "dynamicSQL")
    int deleteByEntityCondition(@Param("entityCondition") EntityCondition<T> condition);

}
