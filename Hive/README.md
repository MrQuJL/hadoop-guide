## Hive

### （一）什么是 Hive

* 构建在 HDFS 之上的一个**数据仓库**

* 同时也是一个支持SQL语言的**数据分析引擎**

* 在 Hive 上执行的复杂的 SQL 将会被转换成一个 MapReduce 程序提交到 Yarn 上运行

* Hive 的数据存储在 HDFS 上，表的元信息（表名，字段名，字段类型，分区信息）存储在 MySql 中

### （二）Hive 的体系结构

![image](https://github.com/MrQuJL/bigdata-guide/blob/master/Hive/imgs/hive-framework.png)

用户接口主要有 3 个：

* CLI(shell命令行)

* JavaAPI(JDBC,ODBC)

* HWI（Web管理界面，已废弃）

### （三）安装和配置

#### 安装 MySql

#### 安装 Hadoop（Hive的数据要存储在HDFS上，所以要安Hadoop）

#### 安装Hive

1. 到Hive官网下载Hive（https://mirrors.tuna.tsinghua.edu.cn/apache/hive/）

2. 解压tar包到指定目录：tar -zxvf apache-hive-2.3.0-bin.tar.gz -C ~/training/

3. 编辑 /etc/profile 文件修改环境变量：

	```shell
	HIVE_HOME=/root/training/apache-hive-2.3.0-bin
	export HIVE_HOME

	PATH=$HIVE_HOME/bin:$PATH
	export PATH
	```

4. 进入hive的conf目录：cp hive-default.xml.template hive-site.xml

5. 编辑hive-site.xml文件内容如下：

	```xml
	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
	<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
	<configuration>
	<property>
	   <name>javax.jdo.option.ConnectionURL</name>
	   <value>jdbc:mysql://localhost:3306/hive?useSSL=false</value>
	</property>

	<property>
	   <name>javax.jdo.option.ConnectionDriverName</name>
	   <value>com.mysql.jdbc.Driver</value>
	</property>

	<property>
	   <name>javax.jdo.option.ConnectionUserName</name>
	   <value>root</value>
	</property>

	<property>
	   <name>javax.jdo.option.ConnectionPassword</name>
	   <value>123</value>
	</property>
	</configuration>
	```

	> 实际上就是配置数据库连接四要素。

6. 将mysql的驱动jar包放到lib目录下（注意一定要使用高版本的MySQL驱动（5.1.43以上的版本））

7. 初始化Hive：

	```shell
	schematool -dbType mysql -initSchema
	```
	
	出现如下信息表示初始化成功：

	```shell
	Starting metastore schema initialization to 2.3.0
	Initialization script hive-schema-2.3.0.mysql.sql
	Initialization script completed
	schemaTool completed
	```

	初始化后的表其实是创建在hive-site.xml指明的数据库(hive)中：
	
	![image](https://github.com/MrQuJL/bigdata-guide/blob/master/Hive/imgs/hive-init.png)

8. Demo演示：

	先启动 HDFS：start-dfs.sh
	
	在启动 Hive 的命令行：hive

	查看表信息：show tables;

	![image](https://github.com/MrQuJL/bigdata-guide/blob/master/Hive/imgs/hive-cli.png)

### （四）Hive 的数据类型


### （五）Hive 的数据模型


### （六）Hive 数据的导入


### （七）Hive 的查询


### （八）Hive 的客户端操作：JDBC


### （九）Hive 的自定义函数（UDF）






