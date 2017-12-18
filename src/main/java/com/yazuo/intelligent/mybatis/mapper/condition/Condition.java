package com.yazuo.intelligent.mybatis.mapper.condition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.util.TypeUtils;
import com.yazuo.intelligent.mybatis.mapper.enums.Logic;
import com.yazuo.intelligent.mybatis.mapper.enums.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.entity.EntityColumn;

import java.util.Collection;


/**        a        =       b   AND
 *单个条件 field operator range logic
 * Created by scvzerng on 2017/5/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    /**
     * 字段名
     */
    private String field;
    /**
     * 列
     */
    private EntityColumn column;
    /**
     * 条件操作符
     */
    private Operator operator;
    /**
     * 值
     */
    private Object value;
    /**
     * 条件连接符  第一个会被丢弃
     */
    private Logic logic;


    public void setColumn(EntityColumn column) {
        this.column = column;
        this.value = converteValue(this.value);

    }



    /**
     * 转换对象类型
     * @param source 源对象
     * @return
     */
    private Object converteValue(Object source){
        if(this.operator==Operator.IN||this.operator==Operator.BETWEEN){
            if(source instanceof String){
                JSONArray ins = JSON.parseArray(source.toString());
                return ins.stream().map(value-> TypeUtils.cast(value,this.column.getJavaType(),null)).toArray();
            }else{
                if(!(source.getClass().isArray())){
                    return  ((Collection)source).toArray();
                }
                throw new RuntimeException(String.format("error range from field:%s type:%s range:%s ",column.getColumn(),column.getJavaType().getSimpleName(),JSON.toJSONString(value)));
            }


        }else{
            return  TypeUtils.cast(source,this.column.getJavaType(),null);

        }
    }
}
