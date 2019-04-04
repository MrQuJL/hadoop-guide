## Hive

### （一）什么是Hive？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/13-Hive/imgs/hive_logo_medium.jpg)

* 构建在Hadoop上的数据仓库平台，为数据仓库管理提供了许多功能

* 起源自facebook由Jeff Hammerbacher领导的团队

* 2008年facebook把hive项目贡献给Apache

* 定义了一种类SQL语言HiveQL。可以看成是仍SQL到Map-Reduce的映射器

* 提供Hive shell、JDBC/ODBC、Thrift客户端等连接

### （二）Hive的体系结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/13-Hive/imgs/hivearc.png)

* 用户接口主要有三个：CLI，JDBC/ODBC和 WebUI

    * CLI，即Shell命令行
    * JDBC/ODBC 是 Hive 的Java，与使用传统数据库JDBC的方式类似
    * WebGUI是通过浏览器访问 Hive

* Hive 将元数据存储在数据库中(metastore)，目前只支持 mysql、derby。Hive 中的元数据包括表的名字，表的列和分区及其属性，表的属性（是否为外部表等），表的数据所在目录等

* 解释器、编译器、优化器完成 HQL 查询语句从词法分析、语法分析、编译、优化以及查询计划（plan）的生成。生成的查询计划存储在 HDFS 中，并在随后有 MapReduce 调用执行

* Hive 的数据存储在 HDFS 中，大部分的查询由 MapReduce 完成（包含 * 的查询，比如 select * from table 不会生成 MapRedcue 任务）

### （三）Hive的安装和配置

* hive下载地址：http://mirrors.shu.edu.cn/apache/hive/

* 解压tar包到指定位置：tar -zxvf apache-hive-2.3.0-bin.tar.gz -C ~/training/

* 将mysql的驱动jar包（5.1.43以上的版本）放入lib目录下

* 修改$HIVE_HOME/conf/hive-site.xml配置文件：

    参数文件 | 配置参数 | 参考值
    ---|---|---
    hive-site.xml | javax.jdo.option.ConnectionURL | jdbc:mysql://localhost:3306/hive?useSSL=false
    ... | javax.jdo.option.ConnectionDriverName | com.mysql.jdbc.Driver
    ... | javax.jdo.option.ConnectionUserName | root
    ... | javax.jdo.option.ConnectionPassword | Welcome_1

* 启动mysql数据库：systemctl start mysqld

* 启动HDFS：start-hdfs.sh

* 初始化Hive的MetaStore：schematool -dbType mysql -initSchema

* 启动Hive：hive

### （四）Hive的数据类型

* 基本数据类型

    * tinyint/smallint/int/bigint: 整数类型
    * float/double: 浮点数类型
    * boolean：布尔类型
    * string：字符串类型

* 复杂数据类型

    * Array：数组类型，由一系列相同数据类型的元素组成
    * Map：集合类型，包含key->value键值对，可以通过key来访问元素
    * Struct：结构类型，可以包含不同数据类型的元。这些元素可以通过"点语法"的方式来得到所需要的元素

* 时间类型

    * Date：从Hive0.12.0开始支持
    * Timestamp：从Hive0.8.0开始支持

### （五）Hive的数据模型

#### 5.1. Hive的数据存储：

* 基于HDFS
* 没有专门的数据存储格式
* 存储结构主要包括：数据库、文件、表、视图
* 可以直接加载文本文件（.txt文件）
* 创建表时，指定Hive数据的列分隔符与行分隔符

#### 5.2. 表：

##### 5.2.1. Inner Table（内部表）

* 与数据库中的Table在概念上是类似的
* 每一个Table在Hive中都有一个相应的目录存储数据
* 所有的Table数据（不包括External Table）都保存在这个目录中
* 删除表时，元数据与数据都会被删除

    ```sql
    create table emp
    (empno int,
    ename string,
    job string,
    mgr int,
    hiredate string,
    sal int,
    comm int,
    deptno int)
    row format delimited fields terminated by ',';
    ```

##### 5.2.2. Partition Table（分区表）

* Partition 对应于数据库的 Partition 列的密集索引

* 在 Hive 中，表中的一个 Partition 对应于表下的一个目录，所有的 Partition 的数据都存储在对应的目录中

    ```sql
    create table emp_part
    (empno int,
    ename string,
    job string,
    mgr int,
    hiredate string,
    sal int,
    comm int)
    partitioned by (deptno int)
    row format delimited fields terminated by ',';
    ```

* 往分区表中插入数据：

    ```sql
    insert into table emp_part partition(deptno=10)
    select empno,ename,job,mgr,hiredate,sal,comm from emp where deptno=10;
    insert into table emp_part partition(deptno=20)
    select empno,ename,job,mgr,hiredate,sal,comm from emp where deptno=20;
    ```

    > insert 语句会转换成一个mapreduce程序，所以需要先启动yarn：start-yarn.sh

##### 5.2.3. External Table（外部表）

* 指向已经在 HDFS 中存在的数据，可以创建 Partition

* 它和内部表在元数据的组织上是相同的，而实际数据的存储则有较大的差异

* 外部表 只有一个过程，加载数据和创建表同时完成，并不会移动到数据仓库目录中，只是与外部数据建立一个链接。当删除一个外部表时，仅删除该链接

    ```sql
    create external table ex_student
    (sid int, sname string, age int)
    row format delimited terminated by ','
    location '/students';
    ```

##### 5.2.4. Bucket Table（桶表）

* 桶表是对数据进行哈希取值，然后放到不同文件中存储

    ```sql
    create table emp_bucket
    (empno int,
    ename string,
    job string,
    mgr int,
    hiredate string,
    sal int,
    comm int,
    deptno int)
    clustered by (job) into 4 buckets
    row format delimited fields terminated by ',';
    ```

    > 注：不能直接向桶表中加载数据，需要使用insert语句插入数据
    
#### 5.3. 视图（View）

* 视图是一种虚表，是一个逻辑概念，可以跨越多张表
* 视图建立在已有表的基础上，视图依赖以建立的这些表称为基表
* 视图的目的是为了简化复杂查询
* ```create view myview as select sname from student1;```

### （六）Hive的数据的导入

* Hive支持两种方式的数据导入

    * 使用load语句导入数据
    * 使用sqoop导入关系型数据库中的数据

* 使用load语句导入数据

    * 数据文件：
    
        ```
        student.csv
        1,Tom,23
        2,Mary,24
        3,Mike,22
        
        create table student(sid int, sname string, age int)
        row format delimited fields terminated by ',';
        ```

    * 导入本地数据文件：
    
        ```sql
        load data local inpath '/root/training/data/student.csv' into table student;
        ```
    
        > 注意：Hive默认分隔符是: tab键。所以需要在建表的时候，指定分隔符。
    
        ```sql
        create table student1
        (sid int,sname string,age int)
        row format delimited fields terminated by ',';
        ```

    * 导入HDFS上的数据：

        ```sql
        create table student2
        (sid int,sname string,age int)
        row format delimited fields terminated by ',';
        ```
        
        ```sql
        load data inpath '/input/student.csv' into table student2;
        ```

* 使用sqoop导入关系型数据库中的数据

    * 将关系型数据的表结构复制到hive中：
    
        ```shell
        sqoop create-hive-table --connect jdbc:mysql://localhost:3306/test --username root --password 123 --table student --hive-table student
        ```
    
        > 注：其中 --table username为mysql中的数据库test中的表   --hive-table test 为hive中新建的表名称
    
    * 从关系数据库导入文件到hive中：
        
        ```shell
        sqoop import --connect jdbc:mysql://localhost:3306/test --username root --password 123 --table student --hive-import
        ```
    
    * 将hive中的数据导入到mysql中：
    
        ```shell
        sqoop export --connect jdbc:mysql://localhost:3306/test --username root --password 123 --table uv_info --export-dir /user/hive/warehouse/uv/dt=2011-08-03
        ```

### （七）Hive的查询




### （八）Hive的客户端操作：JDBC


### （九）Hive的自定义函数


### （十）Hive常见问题解决

* 启动Hive后输入 ```show tables``` 命令报异常：Unable to instantiate org.apache.hadoop.hive.metastore.HiveMetaStoreClient

    在hive-site.xml文件中添加如下配置：
    ```
    <property>
        <name>hive.metastore.schema.verification</name>
        <value>false</value>
    </property>
    ```




