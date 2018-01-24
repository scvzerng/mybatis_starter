package com.zero.scvzerng.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;


@ConfigurationProperties(prefix = PageHelperProperties.PREFIX)
public class PageHelperProperties {
    public static final String PREFIX = "pagehelper";
    /**
     * 分页逻辑
     */
    private String dialect = "";
    /**
     * 指定分页插件使用哪种方言
     */
    private String helperDialect = "postgresql";
    /**
     *  该参数默认为false
     *  设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
     *  和startPage中的pageNum效果一样
     */
    private boolean offsetAsPageNum = true;
    /**
     * 该参数默认为false
     * 设置为true时，使用RowBounds分页会进行count查询
     */
    private boolean rowBoundsWithCount = true;
    /**
     *  设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
     *  相当于没有执行分页查询，但是返回结果仍然是Page类型）
     */
    private boolean pageSizeZero = true;
    /**
     *   3.3.0版本可用 - 分页参数合理化，默认false禁用
     *  启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
     *  禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
     */
    private boolean reasonable = true;
    private String params = "";
    /**
     * 持通过 Mapper 接口参数来传递分页参数
     */
    private boolean supportMethodsArguments = false;
    /**
     * 允许在运行时根据多数据源自动识别对应方言的分页
     */
    private boolean autoRuntimeDialect = false;
    /**
     * 当使用运行时动态数据源或没有设置 helperDialect 属性自动获取数据库类型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认true关闭，设置为 false 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定
     */
    private boolean closeConn = true;

    public Properties getProps(){
        Properties properties = new Properties();
        properties.put("dialect",dialect);
        properties.put("offsetAsPageNum",offsetAsPageNum);
        properties.put("rowBoundsWithCount",rowBoundsWithCount);
        properties.put("pageSizeZero",pageSizeZero);
        properties.put("reasonable",reasonable);
        properties.put("params",params);
        return properties;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getHelperDialect() {
        return helperDialect;
    }

    public void setHelperDialect(String helperDialect) {
        this.helperDialect = helperDialect;
    }

    public boolean isOffsetAsPageNum() {
        return offsetAsPageNum;
    }

    public void setOffsetAsPageNum(boolean offsetAsPageNum) {
        this.offsetAsPageNum = offsetAsPageNum;
    }

    public boolean isRowBoundsWithCount() {
        return rowBoundsWithCount;
    }

    public void setRowBoundsWithCount(boolean rowBoundsWithCount) {
        this.rowBoundsWithCount = rowBoundsWithCount;
    }

    public boolean isPageSizeZero() {
        return pageSizeZero;
    }

    public void setPageSizeZero(boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
    }

    public boolean isReasonable() {
        return reasonable;
    }

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public boolean isSupportMethodsArguments() {
        return supportMethodsArguments;
    }

    public void setSupportMethodsArguments(boolean supportMethodsArguments) {
        this.supportMethodsArguments = supportMethodsArguments;
    }

    public boolean isAutoRuntimeDialect() {
        return autoRuntimeDialect;
    }

    public void setAutoRuntimeDialect(boolean autoRuntimeDialect) {
        this.autoRuntimeDialect = autoRuntimeDialect;
    }

    public boolean isCloseConn() {
        return closeConn;
    }

    public void setCloseConn(boolean closeConn) {
        this.closeConn = closeConn;
    }
}
