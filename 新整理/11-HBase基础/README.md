## HBase

### （一）什么是HBase？


### （二）HBase的体系结构


### （三）HBase的表结构


### （四）HBase的安装和部署


### （五）-ROOT-和.META.


### （六）HBase Shell


### （七）HBase的JavaAPI

* pom依赖如下：

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>1.3.1</version>
</dependency>
```

* 创建表

```java
@Test
public void testCreateTable() throws Exception {
    // 配置信息
    Configuration conf = new Configuration();
    conf.set("hbase.zookeeper.quorum", "192.168.0.1");

    // 创建客户端
    HBaseAdmin admin = new HBaseAdmin(conf);
    // 创建表的描述信息
    HTableDescriptor htd = new HTableDescriptor(TableName.valueOf("students"));
    // 创建列族
    HColumnDescriptor h1 = new HColumnDescriptor("info");
    HColumnDescriptor h2 = new HColumnDescriptor("grade");
    // 将列加入列族
    htd.addFamily(h1);
    htd.addFamily(h2);

    // 创建表
    admin.createTable(htd);
    admin.close();
}
```


### （八）HBase上的过滤器


### （九）HBase上的MapReduce



### （十）HBase的HA













