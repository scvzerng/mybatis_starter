package com.yazuo.intelligent.mybatis.mapper.condition;


import com.yazuo.intelligent.mybatis.mapper.enums.Logic;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *组条件 (a=1 AND b=1) AND 单个条件 c=1
 * Created by scvzerng on 2017/5/8.
 */
@Data
public class ConditionGroup {
    //单个条件
    private List<Condition> conditions = new ArrayList<>(2);
    //链接条件 首个会被截断
    private Logic logic;

}
