## Hive

### （一）什么是Hive？

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

#### Hive的数据存储：

* 基于HDFS
* 没有专门的数据存储格式
* 存储结构主要包括：数据库、文件、表、视图
* 可以直接加载文本文件（.txt文件）
* 创建表时，指定Hive数据的列分隔符与行分隔符

#### 表：

##### 1. Inner Table（内部表）



##### 2. Partition Table（分区表）

##### 3. External Table（外部表）

##### 4. Bucket Table（桶表）

#### 视图（View）

* 视图是一种虚表，是一个逻辑概念，可以跨越多张表
* 视图建立在已有表的基础上，视图依赖以建立的这些表称为基表
* 视图的目的是为了简化复杂查询
* ```create view myview as select sname from student1;```

### （六）Hive的数据的导入


### （七）Hive的查询


### （八）Hive的客户端操作：JDBC


### （九）Hive的自定义函数

