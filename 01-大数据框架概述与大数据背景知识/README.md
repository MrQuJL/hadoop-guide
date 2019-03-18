## 一、Hadoop的起源与背景知识

### （一）什么是大数据

大数据（Big Data），指无法在一定时间范围内用常规软件工具进行捕捉、管理和处理的数据集合，是需要新处理模式才能具有更强的决策力、洞察发现力和流程优化能力的海量、高增长率和多样化的信息资产。

大数据的5个特征（IBM提出）：

* Volume（大量）
* Velocity（高速）
* Variety（多样）
* Value（价值）
* Veracity（真实性）

大数据的典型案例：

* 电商网站的商品推荐

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/product.png)

* 基于大数据的天气预报

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/weather.png)

### （二）OLTP与OLAP

* OLTP：On-Line Transaction Processing（联机事务处理过程）。

	也称为面向交易的处理过程，其基本特征是前台接收的用户数据可以立即传送到计算中心进行处理，并在很短的时间内给出处理结果，是对用户操作快速	响应的方式之一。OLTP是传统的关系型数据库的主要应用，主要是基本的、日常的事务处理，例如银行转账。

* OLAP：On-Line Analytic Processing（联机分析处理过程）。

	OLAP是数据仓库系统的主要应用，支持复杂的分析操作，侧重决策支持，并且提供直观易懂的查询结果。典型案例：商品推荐。


* OLTP和OLAP的区别：

  | OLTP | OLAP
---|---|---
用户 | 操作人员，低层管理人员 | 决策人员，高层管理人员
功能 | 日常操作处理 | 分析决策
DB设计 | 面向应用 | 面向主题
数据 | 当前的，最新的细节的，二维的分立的 | 历史的，聚集的，多维的，集成的，统一的
存取 | 读写数十条记录 | 读上百万条记录
工作单位 | 简单的事务 | 复杂的查询
DB大小 | 100MB-GB | 100GB-TB

### （三）数据仓库

数据仓库，英文名称为Data Warehouse，可简写为DW或DWH。数据仓库，是为企业所有级别的决策制定过程，提供所有类型数据支持的战略集合。它是单个数据存储，出于分析性报告和决策支持目的而创建。

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/datawarehouse.png)

### （四）Hadoop的起源 -- Luence，从Luence到nutch，从nutch到hadoop

* 2003-2004年，Google公开了部分GFS和Mapreduce思想的细节，以此为基础Doug Cutting等人用了2年业余时间实现了DFS和Mapreduce机制，使Nutch性能飙升。

* Yahoo招安Doug Cutting及其项目。

* Hadoop 于 2005 年秋天作为 Lucene的子项目 Nutch的 一部分正式引入Apache基金会。2006 年 3 月份，Map-Reduce 和 Nutch Distributed File System (NDFS) 分别被纳入称为 Hadoop 的项目中

* 名字来源于Doug Cutting儿子的玩具大象。

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/pic.png)

## 二、Apache Hadoop的体系结构

### （一）分布式存储：HDFS

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/dis.png)

* NameNode（名称节点）

1. 维护HDFS文件系统，是HDFS的主节点。

2. 接受客户端的请求: 上传文件、下载文件、创建目录等等。

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/upload.png)

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/download.png)

3. 记录客户端操作的日志（edits文件），保存了HDFS最新的状态.

	1. Edits文件保存了自最后一次检查点之后所有针对HDFS文件系统的操		作，比如：增加文件、重命名文件、删除目录等等

	2. 保存目录：$HADOOP_HOME/tmp/dfs/name/current

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/edits.png)

	3. 可以使用hdfs oev -i命令将日志（二进制）输出为XML文件

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/oev.png)

	输出结果为：
	
	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/result.png)

	4. 维护文件元信息，将内存中不常用（采用LRU算法）的文件元信息保存在硬盘上（fsimage文件）

		1. fsimage是HDFS文件系统存于硬盘中的元数据检查点，里面记录了自		最后一次检查点之前HDFS文件系统中所有目录和文件的序列化信息

		2. 保存目录：$HADOOP_HOME/tmp/dfs/name/current

		3. 可以使用hdfs oiv -i命令将日志（二进制）输出为文本（文本和XML）
		
		![image](https://github.com/MrQuJL/hadoop-guide/blob/master/01-大数据框架概述与大数据背景知识/imgs/oiv.png)