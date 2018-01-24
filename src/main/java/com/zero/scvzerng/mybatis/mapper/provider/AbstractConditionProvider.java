package com.zero.scvzerng.mybatis.mapper.provider;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

import java.util.function.Supplier;

import static com.zero.scvzerng.mybatis.mapper.enums.Operator.BETWEEN;
import static com.zero.scvzerng.mybatis.mapper.enums.Operator.IN;
import static com.zero.scvzerng.mybatis.mapper.enums.Operator.IS_NOT_NULL;
import static com.zero.scvzerng.mybatis.mapper.enums.Operator.IS_NULL;
import static com.zero.scvzerng.mybatis.mapper.enums.Operator.LIKE;
import static com.zero.scvzerng.mybatis.mapper.utils.XMLSqlBuilder.*;


/**
 * Created by scvzerng on 2017/6/13.
 */
public abstract class AbstractConditionProvider extends MapperTemplate {

    public static final String DEFAULT_CONDITION = " ${condition.logic} ${condition.column.column} ${condition.operator.value} ";
    public static final String OPERATOR_ENUM = "condition.operator.value";

    public AbstractConditionProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


    /**
     * 生成sql
     *
     * @return
     */
    protected StringBuilder sql() {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "<where>" +
                        singleConditions() +
                        groupConditions() +
                        "</where>");
        return sql;
    }

    /**
     * 渲染组条件
     *
     * @return
     */
    private StringBuilder groupConditions() {
        StringBuilder group = new StringBuilder();
        group.append(foreach(
                "entityCondition.conditionGroups",
                "group",
                () -> " ${group.logic} " + trim(
                        "(",
                        "AND |OR ",
                        ")",
                        "",
                        () -> foreach(
                                "group.conditions",
                                "condition",
                                renderCondition()
                        ))));
        return group;
    }

    /**
     * 渲染单个条件
     *
     * @return
     */
    private StringBuilder singleConditions() {
        StringBuilder single = new StringBuilder();
        single.append(
                foreach(
                        "entityCondition.conditions",
                        "condition",
                        renderCondition()
                ));
        return single;
    }

    /**
     * 渲染condition
     *
     * @return
     */
    private Supplier<String> renderCondition() {
        return () -> choose(
                () -> when(equalsString(OPERATOR_ENUM, LIKE.getValue()),
                        () ->
                                DEFAULT_CONDITION + "CONCAT('%',#{condition.value},'%')") +
                        when(equalsString(OPERATOR_ENUM, IN.getValue()),
                                () ->
                                        DEFAULT_CONDITION + foreach(
                                                "condition.value",
                                                "value",
                                                "(",
                                                ")",
                                                ",",
                                                () -> "#{value}")
                        ) +
                        when(equalsString(OPERATOR_ENUM, IS_NULL.getValue()),
                                () ->
                                        DEFAULT_CONDITION) +
                        when(equalsString(OPERATOR_ENUM, IS_NOT_NULL.getValue()),
                                () ->
                                        DEFAULT_CONDITION) +
                        when(equalsString(OPERATOR_ENUM, BETWEEN.getValue()),
                                () ->
                                        DEFAULT_CONDITION + " #{condition.value[0]} AND #{condition.value[1]} "), () -> DEFAULT_CONDITION + " #{condition.value} ");
    }

}
