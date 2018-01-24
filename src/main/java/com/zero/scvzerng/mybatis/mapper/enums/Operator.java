package com.zero.scvzerng.mybatis.mapper.enums;

/**
 *条件操作符
 * Created by scvzerng on 2017/5/8.
 */
public enum  Operator {
    EQUALS("="),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    IN("IN"),
    NOT_EQUALS("<>"),
    GREATER_THAN(">"),
    GREATER_THAN_EQUALS(">="),
    LESS_THAN("<"),
    LESS_THAN_EQUALS("<="),
    NOT_IN("NOT IN"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    IS_NOT_NULL("IS NOT NULL"),
    IS_NULL("IS NULL")
    ;
    String value;
    Operator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
