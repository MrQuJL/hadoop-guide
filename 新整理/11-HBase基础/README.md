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

* 插入单条数据

    ```java
    @Test
    public void testPut() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 指定表名
        HTable table = new HTable(conf, "students");
        // 创建一条数据，行键
        Put put = new Put(Bytes.toBytes("stu000"));
        // 指定数据 family 列族 qualifier 列 value 值
        put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("Tom"));

        // 插入数据
        table.put(put);
        table.close();
    }
    ```

* 插入多条数据

    ```java
    @Test
    public void testPutList() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 指定表名
        HTable table = new HTable(conf, "students");

        // 构造集合代表要插入的数据
        List<Put> list = new ArrayList<Put>();
        for (int i = 1; i < 11; i++) {
            Put put = new Put(Bytes.toBytes("stu00" + i));
            put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("Tom" + i));
            // 将数据加入集合
            list.add(put);
        }
        table.put(list);
        table.close();
    }
    ```

* 根据行键查询数据

    ```java
    @Test
    public void testGet() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 指定要查询的表
        HTable table = new HTable(conf, "students");

        // 通过get查询，指定行键
        Get get = new Get(Bytes.toBytes("stu001"));
        // 执行查询
        Result result = table.get(get);

        // 输出结果
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
        table.close();
    }
    ```

* 扫描表中的所有数据

    ```java
    @Test
    public void testScan() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        HTable table = new HTable(conf, "students");
        // 创建一个Scan
        Scan scan = new Scan();

        // 扫描表
        ResultScanner result = table.getScanner(scan);

        // 打印返回的值
        for (Result r : result) {
            System.out.println(Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
        }
        table.close();
    }
    ```

* 删除表

    ```java
    @Test
    public void testDropTable() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 创建客户端
        HBaseAdmin admin = new HBaseAdmin(conf);

        // 先禁用这张表
        admin.disableTable(Bytes.toBytes("students"));
        // 删除表
        admin.deleteTable(Bytes.toBytes("students"));
        admin.close();
    }
    ```

### （八）HBase上的过滤器

* 单一列值过滤器

    类似：select * from students where name = 'Tom1';

    ```java
    @Test
    public void testSingleColumnValueFilter() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 创建客户端查询表
        HTable table = new HTable(conf, "students");
        // 创建一个Scann
        Scan scan = new Scan();
        // 创建一个Filter：SingleColumnValueFilter：姓名为Tom1的
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("info"),
                Bytes.toBytes("name"),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("Tom1"));
        // 使用创建的过滤器
        scan.setFilter(filter);
        // 查询数据
        ResultScanner result = table.getScanner(scan);
        for (Result r : result) {
            // 打印输出
            System.out.println(Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
        }
        table.close();
    }
    ```

* 列名前缀过滤器

    类似：select name from students;

    ```java
    @Test
    public void testColumnPrefixFilter() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 创建HTable进行查询
        HTable table = new HTable(conf, "students");
        // 创建一个Scan
        Scan scan = new Scan();

        // 创建列名前缀过滤器
        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("nam"));
        // ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("name"));
        scan.setFilter(filter);

        // 扫描表
        ResultScanner result = table.getScanner(scan);
        for (Result r : result) {
            // 打印
            System.out.println(Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
        }
        table.close();
    }
    ```

* 多个列名前缀过滤器

    类似 ：select name, gender from students;

    ```java
    @Test
    public void testMultipleColumnPrefixFilter() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 创建客户端查询表
        HTable table = new HTable(conf, "students");
        // 创建一个Scan
        Scan scan = new Scan();

        // 指定我们要查询的多个列
        byte[][] prefixs = new byte[][]{Bytes.toBytes("name"), Bytes.toBytes("gender")};
        // 创建一个MultipleColumnPrefixFilter
        MultipleColumnPrefixFilter filter = new MultipleColumnPrefixFilter(prefixs);
        // 设置Scan的过滤器
        scan.setFilter(filter);

        // 查询数据
        ResultScanner result = table.getScanner(scan);
        for (Result r : result) {
            String name = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")));
            String gender = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("gender")));
            // 打印
            System.out.println(name + "\t" + gender);
        }
        // 关闭客户端
        table.close();
    }
    ```

* 行键过滤器

    类似：select * from students where id = 1;

    ```
    @Test
    public void testRowKeyFilter() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 创建客户端
        HTable table = new HTable(conf, "students");
        // 创建一个Scan
        Scan scan = new Scan();
        // 创建一个Rowkey过滤器
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("stu000"));
        // 使用创建的过滤器
        scan.setFilter(filter);
        // 查询数据
        ResultScanner result = table.getScanner(scan);
        for (Result r : result) {
            String name = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")));
            String gender = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("gender")));
            // 打印
            System.out.println(name + "\t" + gender);
        }
        // 关闭客户端
        table.close();
    }
    ```

* 同时使用多个过滤器

    类似：select name from students where id = 1;

    ```
    @Test
    public void testFilter() throws Exception {
        // 配置信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.0.1");

        // 创建客户端
        HTable table = new HTable(conf, "students");
        // 创建Scan
        Scan scan = new Scan();
        // 第一个过滤器：rowkey过滤器
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("stu002"));
        // 第二个过滤器：列名前缀过滤器
        ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("name"));
        // 创建Filter的List
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(rowFilter);
        filterList.addFilter(columnPrefixFilter);

        scan.setFilter(filterList);
        // 查询数据
        ResultScanner result = table.getScanner(scan);
        for (Result r : result) {
            System.out.println(Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
        }
        table.close();
    }
    ```

### （九）HBase上的MapReduce



### （十）HBase的HA













