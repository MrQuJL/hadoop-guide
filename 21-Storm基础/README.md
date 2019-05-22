## Storm

### （一）什么是Storm？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-log.png)

Storm为分布式实时计算提供了一组通用原语，可被用于“流处理”之中，实时处理消息并更新数据库。这是管理队列及工作者集群的另一种方式。 Storm也可被用于“连续计算”（continuous computation），对数据流做连续查询，在计算时就将结果以流的形式输出给用户。它还可被用于“分布式RPC”，以并行的方式运行昂贵的运算。 

Storm可以方便地在一个计算机集群中编写与扩展复杂的实时计算，Storm用于实时处理，就好比 Hadoop 用于批处理。Storm保证每个消息都会得到处理，而且它很快——在一个小集群中，每秒可以处理数以百万计的消息。更棒的是你可以使用任意编程语言来做开发。

### （二）离线计算和流式计算

#### 离线计算

* 离线计算：批量获取数据、批量传输数据、周期性批量计算数据、数据展示

* 代表技术：Sqoop批量导入数据、HDFS批量存储数据、MapReduce批量计算、Hive

#### 流式计算

* 流式计算：数据实时产生、数据实时传输、数据实时计算、实时展示

* 代表技术：Flume实时获取数据、Kafka/metaq实时数据存储、Storm/JStorm/SparkStreaming/Flink实时数据计算、Redis实时结果缓存、持久化存储(mysql)。

* 一句话总结：将源源不断产生的数据实时收集并实时计算，尽可能快的得到计算结果

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/water.png)

#### Storm与Hadoop的区别

Storm | Hadoop
---|---
Storm用于实时计算 | Hadoop用于离线计算
Storm处理的数据保存在内存中，源源不断 | Hadoop处理的数据保存在文件系统中，一批一批
Storm的数据通过网络传输进来 | Hadoop的数据保存在磁盘中
Storm与Hadoop的编程模型相似 | Storm与Hadoop的编程模型相似

### （三）Storm体系结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/arc1.png)

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/arc2.png)










