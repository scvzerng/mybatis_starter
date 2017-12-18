package com.yazuo.intelligent.mybatis.mapper.condition;

import com.yazuo.intelligent.mybatis.mapper.enums.Logic;
import com.yazuo.intelligent.mybatis.mapper.enums.Operator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ResolvableType;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * 动态查询条件
 * Created by scvzerng on 2017/5/8.
 */
public class EntityCondition {
    //查询实体
    private Map<String, EntityColumn> propertyMap;
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

    private EntityCondition(Class<?> clazz) {
        propertyMap = EntityHelper.getEntityTable(clazz).getPropertyMap();
    }

    public static EntityCondition forClass(Class<?> clazz) {
        return new EntityCondition(clazz);
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions.stream()
                .filter(checkField())
                .peek(checkCondition(logic))
                .collect(toList());
    }

    public void setConditionGroups(List<ConditionGroup> conditionGroups) {
        this.conditionGroups = conditionGroups.stream().peek(group -> {
            if (group.getLogic() == null) group.setLogic(logic);
            group.getConditions().stream().filter(checkField())
                    .forEach(checkCondition(logic));
        }).collect(toList());
    }

    public void setOrderByConditions(List<OrderBy> orderByConditions) {
        this.orders = orderByConditions.stream()
                .filter(orderByCondition -> propertyMap.containsKey(orderByCondition.getField()))
                .peek(order -> order.setEntityColumn(propertyMap.get(order.getField())))
                .collect(toList());
    }


    private Predicate<Condition> checkField() {
        return condition -> propertyMap.containsKey(condition.getField());
    }

    private Consumer<Condition> checkCondition(Logic logic) {
        return condition -> {
            if (condition.getLogic() == null) condition.setLogic(logic);
            condition.setColumn(propertyMap.get(condition.getField()));
        };
    }

    public void addCondition(Logic logic, String field, Operator operator, Object value) {
        this.conditions.add(Condition.builder().field(field).logic(logic).column(propertyMap.get(field)).operator(operator).value(value).build());
    }

    public void addCondition(String field, Object value) {
        this.addCondition(Logic.AND, field, Operator.EQUALS, value);
    }

    public void addConditionGroup(Logic logic, List<Condition> conditions) {
        conditions.stream()
                .filter(condition -> condition.getColumn() == null)
                .forEach(condition -> condition.setColumn(propertyMap.get(condition.getField())));
        this.conditionGroups.add(ConditionGroup.builder().conditions(conditions).logic(logic).build());
    }

    public void addConditionGroup(List<Condition> conditions) {
        this.addConditionGroup(Logic.AND, conditions);
    }

    public void addOrder(OrderBy order) {
        order.setEntityColumn(propertyMap.get(order.getField()));
        this.orders.add(order);
    }


}
