package com.yazuo.intelligent.mybatis.mapper.condition;

import com.yazuo.intelligent.mybatis.mapper.enums.Logic;
import com.yazuo.intelligent.mybatis.mapper.enums.Operator;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * 动态查询条件
 * Created by scvzerng on 2017/5/8.
 */
public class EntityCondition {

    private Map<String, EntityColumn> propertyMap;
    /**
     * 需要查询的列
     */
    @Getter
    @Setter
    private Collection<EntityColumn> columns;
    /**
     * 查询条件 单个条件
     */
    @Getter
    private List<Condition> conditions = new ArrayList<>(1);
    /**
     * 查询条件 组条件
     */
    @Getter
    private List<ConditionGroup> conditionGroups = new ArrayList<>(1);

    /**
     * 默认链接条件符
     */
    @Getter
    @Setter
    private Logic logic = Logic.AND;
    /**
     * 排序条件
     */
    @Getter
    private List<OrderBy> orders = new ArrayList<>(1);

    private EntityCondition(Class<?> clazz) {
        propertyMap = EntityHelper.getEntityTable(clazz).getPropertyMap();
        columns = propertyMap.values();
    }

    /**
     * 初始化实体
     * @param clazz
     * @return
     */
    public static EntityCondition forClass(Class<?> clazz) {
        return new EntityCondition(clazz);
    }

    /**
     * 设置条件  建议配合前端工具类
     * @param conditions
     */
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions.stream()
                .filter(checkField())
                .peek(checkCondition(logic))
                .collect(toList());
    }

    /**
     * 设置组条件 建议配合前端工具类
     * @param conditionGroups
     */
    public void setConditionGroups(List<ConditionGroup> conditionGroups) {
        this.conditionGroups = conditionGroups.stream().peek(group -> {
            if (group.getLogic() == null) group.setLogic(logic);
            group.getConditions().stream().filter(checkField())
                    .forEach(checkCondition(logic));
        }).collect(toList());
    }

    /**
     * 设置排序 建议配合前端工具类
     * @param orderByConditions
     */
    public void setOrderByConditions(List<OrderBy> orderByConditions) {
        this.orders = orderByConditions.stream()
                .filter(orderByCondition -> propertyMap.containsKey(orderByCondition.getField()))
                .peek(order -> order.setEntityColumn(propertyMap.get(order.getField())))
                .collect(toList());
    }

    /**
     * 过滤指定字段
     * @param fields 需要过滤的字段
     */
    public void filter(String... fields){
       List<String> filterFields = Arrays.asList(fields);
      this.columns = propertyMap.values()
              .stream()
              .filter(entityColumn -> !filterFields.contains(entityColumn.getProperty()))
              .collect(toList());
    }

    /**
     * 仅保留指定字段
     * @param fields 需要保留的字段
     */
    public void include(String... fields){
        List<String> filterFields = Arrays.asList(fields);
        this.columns = propertyMap.values()
                .stream()
                .filter(entityColumn -> filterFields.contains(entityColumn.getProperty()))
                .collect(toList());
    }

    /**
     * 检查字段是否合法
     * @return
     */
    private Predicate<Condition> checkField() {
        return condition -> propertyMap.containsKey(condition.getField());
    }

    /**
     * 检查和初始化condition
     * @param logic
     * @return
     */
    private Consumer<Condition> checkCondition(Logic logic) {
        return condition -> {
            if (condition.getLogic() == null) condition.setLogic(logic);
            condition.setColumn(propertyMap.get(condition.getField()));
        };
    }

    /**
     * 添加条件
     * @param logic 逻辑连接符
     * @param field 属性名称
     * @param operator 操作类型
     * @param value 值
     */
    public void addCondition(Logic logic, String field, Operator operator, Object value) {
        this.conditions.add(Condition.builder().field(field).logic(logic).column(propertyMap.get(field)).operator(operator).value(value).build());
    }

    /**
     * 添加 AND field = value 条件
     * @param field 属性名称
     * @param value 属性值
     */
    public void addCondition(String field, Object value) {
        this.addCondition(Logic.AND, field, Operator.EQUALS, value);
    }

    /**
     * 添加条件组
     * @param logic 逻辑连接符
     * @param conditions 一组条件
     */
    public void addConditionGroup(Logic logic, List<Condition> conditions) {
        conditions.stream()
                .filter(condition -> condition.getColumn() == null)
                .forEach(condition -> condition.setColumn(propertyMap.get(condition.getField())));
        this.conditionGroups.add(ConditionGroup.builder().conditions(conditions).logic(logic).build());
    }

    /**
     * 添加AND连接符条件组
     * @param conditions 一组条件
     */
    public void addConditionGroup(List<Condition> conditions) {
        this.addConditionGroup(Logic.AND, conditions);
    }

    /**
     * 添加排序
     * @param order 排序
     */
    public void addOrder(OrderBy order) {
        order.setEntityColumn(propertyMap.get(order.getField()));
        this.orders.add(order);
    }


}
