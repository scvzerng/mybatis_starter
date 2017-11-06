package com.yazuo.intelligent.datasource.config;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/10/17-22:05
 * Project:intelligent_starter_parent
 * Package:com.yazuo.intelligent.datasource.config
 * To change this template use File | Settings | File Templates.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceConfig {
    /**
     * 驱动类名
     */
    private String driverClassName;
    /**
     * 初始化时建立物理连接的个数
     */
    private Integer initialSize;
    /**
     * 最小连接池数量
     */
    private Integer minIdle;
    /**
     * 已经不再使用，配置了也没效果
     */
    @Deprecated
    private Integer maxIdle;
    /**
     * 最大连接池数量
     */
    private Integer maxActive;
    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，
     * 缺省启用公平锁，并发效率会有所下降，
     *如果需要可以通过配置useUnfairLock属性为true使用非公平锁
     */
    private Integer maxWait;
    /**
     * 链接是否默认提交
     */
    private Boolean defaultAutoCommit = false;
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句
     */
    private String validationQuery = "SELECT 'x'";
    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnBorrow = false;
    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnReturn = false;
    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private Boolean poolPreparedStatements = false;
    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = 0 ;
    private Boolean removeAbandoned;
    private Integer removeAbandonedTimeout;
    /**
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
     */
    private Boolean testWhileIdle = true;
    /**
     * 有两个含义：
     *1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
     *2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private Integer timeBetweenEvictionRunsMillis;
    private Integer numTestsPerEvictionRun;
    private String filters = "stat,wall";
    /**
     * 连接保持空闲而不被驱逐的最小时间
     */
    private Integer minEvictableIdleTimeMillis;
    /**
     * 连接数据库的url
     */
    private String url;
    /**
     * 连接数据库的用户名
     */
    private String username;
    /**
     * 连接数据库的密码
     */
    private String password;

}
