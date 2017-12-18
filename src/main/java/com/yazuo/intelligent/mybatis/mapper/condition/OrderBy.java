package com.yazuo.intelligent.mybatis.mapper.condition;

import com.yazuo.intelligent.mybatis.mapper.enums.Order;
import lombok.Builder;
import lombok.Data;
import tk.mybatis.mapper.entity.EntityColumn;

/**
 * description
 * <p>
 * 2017-12-15 15:33
 *
 * @author scvzerng
 **/
@Data
public class OrderBy {
    private Order order;
    private String field;
    private EntityColumn entityColumn;
    public OrderBy(String field) {
        this.field = field;
        this.order = Order.DESC;
    }

    public static OrderBy asc(String field){
        OrderBy orderBy = new OrderBy(field);
        orderBy.setOrder(Order.ASC);
        return orderBy;
    }

    public static OrderBy desc(String field){
       return new OrderBy(field);
    }

}
