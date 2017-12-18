## mybatis starter

> pagehelper前缀  具体配置PageHelperProperties

```yaml
pagehelper: 
  dialect: "" #分页逻辑
  helperDialect: postgresql #指定分页插件使用哪种方言
  offsetAsPageNum: true 
  rowBoundsWithCount: true
  pageSizeZero: true
  reasonable: true
  params: ""
  supportMethodsArguments: false
  autoRuntimeDialect: false
  closeConn: true
```
> intelligent.datasource前缀 具体配置 DataSourceProperties
- 多数据源
> mybatis前缀 具体配置MybatisMapperProperties

```yaml
mybatis:
  config: #MyBatis 的 XML 配置文件路径
  mapperLocations: #Mapper XML文件所在位置
  typeAliasesPackage: #扫描类型别名所在包
  typeHandlersPackage: com.yazuo #扫描类型处理器所在包
  checkConfigLocation: false #是否检查 mybatis XML配置
  executorType: ExecutorType.SIMPLE # SQL执行器
  mappers: Collections.singletonList(Mapper.class) #需要注册的Mapper                                
```

- 多数据源事物支持

```yaml
intelligent:
  datasource:
    master: xiaoyaFinance #默认数据源
    data-sources:
       xiaoyaFinance: #key作为切换数据源@DataSource 中的value
               driverClassName: "" #驱动类名
               initialSize: 5 #初始化时建立物理连接的个数
               minIdle: 5 #最小连接池数量
               maxIdle: 20 #已经不再使用，配置了也没效果
               maxActive: 100 #最大连接池数量
               maxWait: 100000 # 获取连接时最大等待时间，单位毫秒。配置了maxWait之后， 缺省启用公平锁，并发效率会有所下降，
               defaultAutoCommit: false #链接是否默认提交
               validationQuery: "SELECT 'x'" #用来检测连接是否有效的sql，要求是一个查询语句
               testOnBorrow: false #申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
               testOnReturn: false #归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
               poolPreparedStatements: false #是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
               maxPoolPreparedStatementPerConnectionSize: false #要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
               removeAbandoned: true
               removeAbandonedTimeout: 600
               testWhileIdle: true #申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
               timeBetweenEvictionRunsMillis: 60000 #Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接
               filters: stat,wall
               numTestsPerEvictionRun: 20 
               minEvictableIdleTimeMillis: 300000 #连接保持空闲而不被驱逐的最小时间
               url: jdbc:postgresql://192.168.49.62:5432/xiaoya_crm?ApplicationName=xiaoyaFinance #连接数据库的url
               username: xycrm #连接数据库的用户名
               password: xycrm #连接数据库的密码
  enable: false #启用数据源 默认开启
```
- UUID类型处理器
> 其他数据源通过 DataSourceBuilder接口拓展后 @Bean直接注入
- 默认支持druid

### @DataSource

- 注解切换数据源 value写上配置时填的key即可

### EntityCondition

**代码**

```java
  EntityCondition entityCondition = EntityCondition.forClass(AclStore.class);
        entityCondition.addCondition("storeStatus",2);
        entityCondition.addCondition("storeStatus",1);
        entityCondition.addCondition(Logic.OR,"storeStatus",Operator.IN,Arrays.asList(1,2,3,5));
        entityCondition.addCondition(Logic.OR,"storeStatus",Operator.BETWEEN,Arrays.asList(1,2));
        entityCondition.addCondition(Logic.OR,"storeStatus",Operator.IS_NOT_NULL,null);
        entityCondition.addConditionGroup(Arrays.asList(
                Condition.builder().field("storeStatus").logic(Logic.AND).operator(Operator.BETWEEN).value(Arrays.asList(1,2)).build(),
                Condition.builder().field("storeStatus").logic(Logic.OR).operator(Operator.IN).value(Arrays.asList(1,2,3)).build())
        );
        entityCondition.addOrder(OrderBy.asc("aclStoreType"));
        entityCondition.addOrder(OrderBy.desc("storeStatus"));
        PageHelper.startPage(page,size).doSelectPage(()->aclStoreMapper.selectByEntityCondition(entityCondition));
```

**生成sql**

```sql
SELECT
	ID,
	tenant_id,
	code,
	NAME,
	parent_id,
	org_type,
	country,
	province,
	city,
	district,
	address,
	contact_man,
	contact_number,
	order_no,
	create_user,
	create_time,
	update_user,
	update_time,
	delete_flag,
	status,
	brand_id,
	acl_store_type,
	ten_alias,
	alip_mer_id,
	alip_sto_id,
	short_pinyin,
	is_show_card,
	store_logo,
	referrer,
	key_account_type,
	consignor,
	store_status,
	tabl_cons,
	tabl_cons_real,
	rate_margin,
	tab_num,
	dic_profession_id,
	latitude,
	longitude,
	pre_capita,
	main_shop_name,
	merchant_id,
	merchant_parent_id,
	merchant_brand_id,
	ali_store_logo_id,
	daya_merchant_id,
	plus_flag,
	plus_status
FROM
	xiaoya_crm.acl_store
WHERE
	store_status = ?
AND store_status = ?
OR store_status IN (?,?,?,?)
OR store_status BETWEEN ?
AND ?
OR store_status IS NOT NULL
AND (
	store_status BETWEEN ?
	AND ?
	OR store_status IN (?,?,?)
)
ORDER BY
	acl_store_type ASC,
	store_status DESC
LIMIT 2
```