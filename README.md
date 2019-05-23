## 大数据学习指南

### 概述：
传统的 OLTP系统的数据一般是存储在关系型数据库中，数据的处理也只是由单台服务器来完成，即使是某个服务集群部署也只是每台机器独自处理一个来自客户端的请求，并不是所有机器共同处理这一个请求。

随着业务的发展，数据量的不断沉淀，不少企业都已经积攒了 TB，PB 甚至 EB 级别的数据。Spring，MySQL 那一套传统的 OLTP 系统的架构已经无法存储以及计算如此庞大的数据。




### 第一代计算引擎：Hadoop


### 第二代计算引擎：Spark


### 第三代计算引擎：Flink




1. *Java*基础和*Linux*基础





2. Hadoop的学习：体系结构、原理、编程
	```	
	* 第一阶段：HDFS（文件系统）、MapReduce（java程序）、HBase（NoSQL数据库）
	* 第二阶段：数据分析引擎 ---> Hive、Pig
		数据采集引擎 ---> Sqoop、Flume
	* 第三阶段：HUE：Web管理工具
		ZooKeeper：实现Hadoop的HA
		Oozie：工作流引擎
	```
3. Spark的学习
	```
	* 第一个阶段：Scala编程语言
	* 第二个阶段：Spark Core-----> 基于内存，数据的计算
	* 第三个阶段：Spark SQL -----> 类似Oracle中的SQL语句
	* 第四个阶段：Spark Streaming ---> 进行实时计算（流式计算）：比如：自来水厂
	```
4. Apache Storm：类似Spark Streaming ---> 进行实时计算（流式计算）：比如：自来水厂 
	```
	* NoSQL：Redis基于内存的数据库（学习中...）
	```



更多精彩内容，可以访问[曲健磊的博客-hadoop专场][1]

[1]: https://blog.csdn.net/a909301740/column/info/29697 "曲健磊的博客-hadoop专场"





