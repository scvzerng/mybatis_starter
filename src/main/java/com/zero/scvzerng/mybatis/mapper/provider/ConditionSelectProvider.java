package com.zero.scvzerng.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import static com.zero.scvzerng.mybatis.mapper.utils.XMLSqlBuilder.*;


/**
 * 条件查询Mapper
 * Created by scvzerng on 2017/5/8.
 */
public class ConditionSelectProvider extends AbstractConditionProvider {

    public ConditionSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectByEntityCondition(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();

        //支持查询指定列
        sql.append(getColumns());
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(sql());
        sql.append(getOrderByCondition());
        return sql.toString();
    }

    private String getColumns(){
        return trim(
                "SELECT ",
                "",
                "",
                ",",
                ()->
                        foreach(
                                "entityCondition.columns",
                                "column",
                                " ",
                                " ",
                                ",",
                                ()->
                                        "${column.column}")
        );
    }

    private String getOrderByCondition() {
        return when(
                notEquals("entityCondition.orders", "null"),
                () ->
                        trim(
                        "ORDER BY ",
                        "",
                        "",
                        ",",
                        () ->
                                 foreach(
                                "entityCondition.orders",
                                "order",
                                "",
                                "",
                                ","
                               ,
                                () -> "${order.entityColumn.column} ${order.order} ")));
    }

}
