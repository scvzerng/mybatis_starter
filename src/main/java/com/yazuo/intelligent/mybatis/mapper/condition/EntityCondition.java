package com.yazuo.intelligent.mybatis.mapper.condition;

import com.yazuo.intelligent.mybatis.mapper.enums.Logic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ResolvableType;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 *  动态查询条件
 * Created by scvzerng on 2017/5/8.
 */
public class EntityCondition<T> {
    //查询实体
    private EntityTable entity;
    @Getter
    //查询条件 单个条件
    private List<Condition> conditions = new ArrayList<>(1);
    @Getter
    //查询条件 组条件
    private List<ConditionGroup> conditionGroups = new ArrayList<>(1);
    @Getter
    @Setter
    //默认链接条件符
    private Logic logic = Logic.AND;
    @Getter
    //增加排序条件
    private List<OrderBy> orders = new ArrayList<>(1);

    @SuppressWarnings("unchecked")
    public EntityCondition() {
        entity = EntityHelper.getEntityTable(ResolvableType.forInstance(this).getGeneric(0).resolve());
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions.stream()
                .filter(checkField())
                .peek(checkCondition(logic))
                .collect(toList());
    }

    public void setConditionGroups(List<ConditionGroup> conditionGroups) {
        this.conditionGroups = conditionGroups.stream().peek(group -> {
            if(group.getLogic()==null) group.setLogic(logic);
            group.getConditions().stream().filter(checkField())
                    .forEach(checkCondition(logic));
        }).collect(toList());
    }

    public void setOrderByConditions(List<OrderBy> orderByConditions) {
        this.orders = orderByConditions.stream().filter(orderByCondition->entity.getPropertyMap().containsKey(orderByCondition.getField())).collect(toList());
    }


     private Predicate<Condition> checkField(){
        return condition->entity.getPropertyMap().containsKey(condition.getField());
     }

     private Consumer<Condition> checkCondition(Logic logic){
         return condition->{
             if(condition.getLogic()==null) condition.setLogic(logic);
             condition.setColumn(entity.getPropertyMap().get(condition.getField()));
         };
     }




}
