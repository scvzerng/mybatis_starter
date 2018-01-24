package com.zero.scvzerng.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;


/**
 *条件查询Mapper
 * Created by scvzerng on 2017/5/8.
 */
public class ConditionRemoveProvider extends AbstractConditionProvider {
    public ConditionRemoveProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }
    public String deleteByEntityCondition(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(sql());
        return sql.toString();
    }


}
