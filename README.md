## 大数据学习指南

### 概述：
传统的 OLTP系统的数据一般是存储在关系型数据库中，数据的处理也只是由单台服务器来完成，即使是某个服务集群部署也只是每台机器独自处理一个来自客户端的请求，并不是所有机器共同处理这一个请求。

随着业务的发展，数据量的不断沉淀，不少企业都已经积攒了 TB，PB 甚至 EB 级别的数据。Spring，MySQL 那一套传统的 OLTP 系统的架构已经无法存储以及计算如此庞大的数据，Hadoop 应运而生。

> PS：点击图片可以跳转到相应的部分。

### 第一代计算引擎：Hadoop
------
[![image](https://github.com/MrQuJL/hadoop-guide/blob/master/02-搭建Hadoop的环境/imgs/hadoop-logo.jpg)](https://github.com/MrQuJL/hadoop-guide/tree/master/02-搭建Hadoop的环境)

Hadoop 通过 **HDFS** 将一个文件切分成多个数据块，分开存储在各个节点上，并且在每个节点进行冗余备份以实现高可用，解决了海量数据的存储问题；通过 **MapReduce** 将程序分发到不同的节点上，每个程序只负责处理待处理数据的的一部分，所有程序同时执行，最终每个程序的执行结果进行汇总，解决了海量数据的计算问题。

#### 1. HDFS：Hadoop Distributed File System

[![image](https://github.com/MrQuJL/hadoop-guide/blob/master/02-搭建Hadoop的环境/imgs/hdfs-logo.jpg)](https://github.com/MrQuJL/hadoop-guide/tree/master/04-操作HDFS)

不管是学习 Hadoop，Spark 还是 Flink，我们首先要学习如何安装配置使用 HDFS 分布式文件系统，这是大数据的基石。我们平常开发所使用的 MapReduce 程序，Spark 程序，Hive 底层都是操作的 HDFS 上的数据，学习 HDFS 的安装配置和使用是首要任务。

#### 2. MapReduce


当我们已经学会如何操作底层的 HDFS 之后，就可以写一些 MapReduce 程序来读取 HDFS 上的数据进行一些业务操作。

#### 3. Hive

使用了一段时间 MapReduce 程序之后我们会发现在通过 MapReduce 框架来进行一些多表关联操作有些麻烦。Hive 应运而生。我们只需要执行一条 HQL（Hive SQL）命令，Hive 就会自动把它转化成一个 MapReduce 程序提交到 Yarn 上执行。

#### 4. Pig

Pig 和 Hive 是类似的东西。我们也可以通过写一条 PigLatin 语句，Pig 会自动将其转换成 MapReduce 程序提交到 Yarn 上执行。

> PS：在企业中，Hive 用的多一点。

#### 5. Sqoop

无论是直接写 MapReduce 程序，还是用 Hive，Pig 执行查询语句，间接生成 MapReduce 程序，我们用到的 HDFS 上的数据可能是我们手动上传的本地测试数据。实际生产环境的数据可能来自我们的业务数据库，埋点，日志，Python爬虫爬取的公共平台的数据。而 MapReduce 程序读取的是 HDFS 上的数据，那我们如何把业务系统中的数据导入到 HDFS 上呢？

针对关系型数据库（MySql，Oracle），我们通常选择使用 Sqoop，执行一条 Sqoop 命令将关系型数据库中的数据导入到 HDFS 上。当然，也可以将 HDFS 上的数据导出到关系型数据库中。

> PS：Sqoop 导关系型数据库中的数据底层其实就是使用 JDBC 的方式，所以在导数据的时候会对数据库造成较大的压力。小公司，可能无所谓，本身业务量不大，使用 Sqoop 就比较方便；大公司，巨大的访问量可能不允许你直接操作线上的业务库，而且你也不一定有访问数据库的权限，所以一般的做法是，通知 DBA 将关系型数据库中的数据导出成 CSV 文件，通过写脚本上传或通过 Flume 采集的方式将数据上传到 HDFS 。

#### 6. Flume

在介绍 Sqoop 的时候也提到了，可以通过 Flume 将 CSV 文件采集到 HDFS。Flume 主要还是用来采集日志。Flume 通过监控某个目录，每当目录中有新的文件产生时，就将文件上传到 HDFS。

#### 7. HBase

HDFS 上的数据是位于磁盘上的，直接访问磁盘效率比较低，访问内存速度比较快。为了实现海量数据的快速查询就有了 HBase，HBase 就是基于 HDFS 的 NoSQL。

#### 8. Zookeeper


#### 9. Oozie


#### 10. Storm


### 第二代计算引擎：Spark
------


### 第三代计算引擎：Flink
------



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





