## HBase

### （一）什么是HBase？

一个底层存储依赖于HDFS，分布式依赖于zookeeper，面向列的开源NoSql数据库。

### （二）HBase的体系结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/11-HBase基础/imgs/hbasearc.png)

* HMaster:

    1. 管理用户对Table的增删改查操作。

    2. 管理HRegionServer服务器之间的负载均衡，调整Region的分布。

    3. 数据量过大导致Region分裂后，负责分配新的Region。

    4. 在Region服务器停机后，负责失效Region服务器上Regiion的迁移。

* HRegionServer: 存储Region的服务器。

* Region: Region是HBase数据存储和管理的基本单位。

* Store: Region中由多个Store组成，每个Store对应表中的一个CF（列族）。Store由两部分组成：MemStore，StoreFie。

* MemStore: 是一个写缓存，对表中数据的操作首先写WAL日志，然后才写入MemStore，MemStore满了之后会Flush成一个StoreFile（底层实现是HFile）

* Sotre file: 对HFile的一层封装。

* HFile: 真正用于存储HBase数据的文件。在HFile中的数据是按照RowKey，CF，Column排序。位于HDFS上。

* zookeeper: HBase的客户端首先需要访问zookeeper获取RegionServer的地址然后才能操作RegionServer。

### （三）HBase的表结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/11-HBase基础/imgs/hbasetable.png)

### （四）HBase的安装和部署

* 下载HBase：https://hbase.apache.org/downloads.html

* 解压到Linux上的指定目录，修改conf目录下的如下文件：

* 本地模式配置：

    参数文件 | 配置参数 | 参考值
    ---|---|---
    hbase-env.sh | JAVA_HOME | /root/training/jdk1.8.0_144
    hbase-site.xml | hbase.rootdir | file:///root/training/hbase-1.3.1/data

* 伪分布模式配置：

    参数文件 | 配置参数 | 参考值
    ---|---|---
    hbase-env.sh | JAVA_HOME | /root/training/jdk1.8.0_144
    ... | HBASE_MANAGES_ZK | true
    hbase-site.xml | hbase.rootdir | file:///root/training/hbase-1.3.1/data
    ... | hbase.cluster.distributed | true
    ... | hbase.zookeeper.quorum | 192.168.157.111
    ... | dfs.replication | 1
    regionservers | | 192.168.157.111

* 全分布模式配置：

    参数文件 | 配置参数 | 参考值
    ---|---|---
    hbase-env.sh | JAVA_HOME | /root/training/jdk1.8.0_144
    ... | HBASE_MANAGES_ZK | true
    hbase-site.xml | hbase.rootdir | file:///root/training/hbase-1.3.1/data
    ... | hbase.cluster.distributed | true
    ... | hbase.zookeeper.quorum | 192.168.157.111
    ... | dfs.replication | 2
    ... | hbase.master.maxclockskew | 180000
    regionservers | | 192.168.157.111
    ... | | 192.168.157.112

* 配置xml文件的一般格式(仅供参考)：

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>hbase.rootdir</name>
            <value>file:///root/training/hbase-1.3.1/data</value>
        </property>
        <property>
            <name>hbase.rootdir</name>
            <value>file:///root/training/hbase-1.3.1/data</value>
        </property>
        <property>
            <name>hbase.rootdir</name>
            <value>file:///root/training/hbase-1.3.1/data</value>
        </property>
    </configuration>
    ```

* 启动HDFS：```start-hdfs.sh```

* 启动zookeeper：```zkServer.sh start```

* 启动HBase：```start-hbase.sh```

* 启动HBase Shell：```hbase shell```

* 在web端查看HMaster和RegionServer的情况：localhost:16010

### （五）-ROOT-和.META.

* HBase中有两张特殊的表，-ROOT- 和 .META.

	* -ROOT-: 记录了.META.表的Region信息,-ROOT-只有一个region
	
	* .META.: 记录了用户创建表的Region信息，.META.可以有多个Region

* zookeeper中记录了-ROOT-表的位置

* Client访问用户数据之前需要首先访问zookeeper，获取-ROOT-表的位置，然后访问-ROOT-表，接着访问.META.表，最后才能找到用户数据的位置去访问。


### （六）HBase Shell

* 命令格式如下：

	名称 | 命令表达式
	---|---
	创建表 | create '表名称','列族名称1','列族名称2','列族名称N'
	添加记录 | put '表名称','列族名称','列名称','值'
	查看记录 | get '表名称','行键'
	查看表中的记录数 | count '表名称'
	删除记录 | delete '表名',"行键",'列族名称:列名称'
	删除表 | 先要屏蔽该表，第一步 disable '表名称' 第二步 drop '表名称'
	查看所有记录 | scan '表名称'
	查看某个表某个列的所有数据 | scan 'students',{COLUMNS=>'列族名称:列名称'}
	更新记录 | 就是重新put一遍进行覆盖

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

    ```java
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

    ```java
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

* pom依赖暂时没有找到，需要的jar包是hbase的lib目录下的所有jar包

* 测试数据：

	```shell
	create 'word','content'
	put 'word','1','content:info','I love Beijing'
	put 'word','2','content:info','I love China'
	put 'word','3','content:info','Beijing is the capital of China'

	create 'stat','content'
	```

* Mapper：

	```java
	import org.apache.hadoop.hbase.client.Result;
	import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
	import org.apache.hadoop.hbase.mapreduce.TableMapper;
	import org.apache.hadoop.hbase.util.Bytes;
	import org.apache.hadoop.io.IntWritable;
	import org.apache.hadoop.io.Text;

	import java.io.IOException;

	/**
	 * @author 曲健磊
	 * @date 2019-03-14 19:23:39
	 */
	public class MyMapper extends TableMapper<Text, IntWritable> {
		@Override
		protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
			// 读入的数据：HBase表中的数据--->word表
			String words = Bytes.toString(value.getValue(Bytes.toBytes("content"), Bytes.toBytes("info")));

			// 分词：I love Beijing
			String[] itr = words.split(" ");

			for (String w : itr) {
				// 直接输出
				Text w1 = new Text();
				w1.set(w);
				context.write(w1, new IntWritable(1));
			}
		}
	}
	```

* Reducer：

	```java
	import org.apache.hadoop.hbase.client.Put;
	import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
	import org.apache.hadoop.hbase.mapreduce.TableReducer;
	import org.apache.hadoop.hbase.util.Bytes;
	import org.apache.hadoop.io.IntWritable;
	import org.apache.hadoop.io.Text;

	import java.io.IOException;

	/**
	 * @author 曲健磊
	 * @date 2019-03-15 14:08:54
	 */
	public class MyReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			// 求和
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			// 输出---> HBase表
			// 构造Put,可以使用key作为行键
			Put put = new Put(Bytes.toBytes(key.toString()));

			// 封装数据
			put.add(Bytes.toBytes("content"), Bytes.toBytes("info"), Bytes.toBytes(String.valueOf(sum)));

			// 写入HBase
			context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())), put);
		}
	}
	```

* Main:

	```java
	import org.apache.hadoop.conf.Configuration;
	import org.apache.hadoop.hbase.client.Scan;
	import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
	import org.apache.hadoop.hbase.util.Bytes;
	import org.apache.hadoop.io.IntWritable;
	import org.apache.hadoop.io.Text;
	import org.apache.hadoop.mapreduce.Job;

	import java.io.IOException;

	/**
	 * @author 曲健磊
	 * @date 2019-03-15 14:14:41
	 */
	public class MyDriver {
		public static void main(String[] args) throws Exception {
			Configuration conf = new Configuration();
			conf.set("hbase.zookeeper.quorum", "127.0.0.1");

			// 创建Job
			Job job = Job.getInstance(conf);
			job.setJarByClass(MyDriver.class);

			// 创建Scan
			Scan scan = new Scan();
			// 可以指定查询的某一列
			scan.addColumn(Bytes.toBytes("content"), Bytes.toBytes("info"));

			// 指定查询HBase表的Mapper
			TableMapReduceUtil.initTableMapperJob("word", scan, MyMapper.class, Text.class, IntWritable.class, job);

			// 指定写入HBase表的Reducer
			TableMapReduceUtil.initTableReducerJob("stat", MyReducer.class, job);

			job.waitForCompletion(true);
		}
	}
	```

* 打成jar包，上传到服务器上

* 启动HDFS：start-hdfs.sh

* 启动Yarn：start-yarn.sh

* 启动zookeeper：zkServer.sh start

* 启动HBase：start-hbase.sh

* 提交jar包到hadoop集群上运行：hadoop jar xxx.jar

* 通过hbase shell查看运行结果：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/11-HBase基础/imgs/hbasemapreduce.png)

### （十）HBase的HA

* 架构：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/11-HBase基础/imgs/arc.png)

* 在一个RegionServer上单独启动一个HMaster

	```
	hbase-daemon.sh start master
	```

