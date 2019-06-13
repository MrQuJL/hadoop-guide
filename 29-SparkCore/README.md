## Spark Core

### （一）什么是Spark？

1. 什么是 Spark？

   ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/spark.png)

   **Spark 是一个针对大规模数据处理的快速通用引擎。**

   Spark是一种快速、通用、可扩展的大数据分析引擎，2009年诞生于加州大学伯克利分校AMPLab，2010年开源，2013年6月成为Apache孵化项目，2014年2月成为Apache顶级项目。目前，Spark生态系统已经发展成为一个包含多个子项目的集合，其中包含SparkSQL、Spark Streaming、GraphX、MLlib等子项目，Spark是基于内存计算的大数据并行计算框架。Spark基于内存计算，提高了在大数据环境下数据处理的实时性，同时保证了高容错性和高可伸缩性，允许用户将Spark部署在大量廉价硬件之上，形成集群。Spark得到了众多大数据公司的支持，这些公司包括Hortonworks、IBM、Intel、Cloudera、MapR、Pivotal、百度、阿里、腾讯、京东、携程、优酷土豆。当前百度的Spark已应用于凤巢、大搜索、直达号、百度大数据等业务；阿里利用GraphX构建了大规模的图计算和图挖掘系统，实现了很多生产系统的推荐算法；腾讯Spark集群达到8000台的规模，是当前已知的世界上最大的Spark集群。

2. 为什么要学习 Spark？

   * Hadoop的MapReduce计算模型存在的问题：

     学习过Hadoop的MapReduce的学员都知道，MapReduce的核心是Shuffle（洗牌）。在整个Shuffle的过程中，至少会产生6次的I/O。下图是我们在讲MapReduce的时候，画的Shuffle的过程。

     中间结果输出：基于MapReduce的计算引擎通常会将中间结果输出到磁盘上，进行存储和容错。另外，当一些查询（如：Hive）翻译到MapReduce任务时，往往会产生多个Stage（阶段），而这些串联的Stage又依赖于底层文件系统（如HDFS）来存储每一个Stage的输出结果，而I/O的效率往往较低，从而影响了MapReduce的运行速度。

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/shuffle.png)

   * Spark 的最大特点：基于内存

     Spark 是 MapReduce 的替代方案，而且兼容 HDFS、Hive，可融入 Hadoop 的生态系统，以弥补 MapReduce 的不足。

3. Saprk 的特点：快、易用、通用、兼容性

   * 快

     与Hadoop的MapReduce相比，Spark基于内存的运算速度要快100倍以上，即使，Spark基于硬盘的运算也要快10倍。Spark实现了高效的DAG执行引擎，从而可以通过内存来高效处理数据流。

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/speed.png)

   * 易用

      Spark支持Java、Python和Scala的API，还支持超过80种高级算法，使用户可以快速构建不同的应用。而且Spark支持交互式的Python和Scala的shell，可以非常方便地在这些shell中使用Spark集群来验证解决问题的方法。

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/easy.png)

   * 通用

      Spark提供了统一的解决方案。Spark可以用于**批处理**、交互式查询（**Spark SQL**）、实时流处理（**Spark Streaming**）、机器学习（**Spark MLlib**）和图计算（**GraphX**）。这些不同类型的处理都可以在同一个应用中无缝使用。Spark统一的解决方案非常具有吸引力，毕竟任何公司都想用统一的平台去处理遇到的问题，减少开发和维护的人力成本和部署平台的物力成本。

      另外Spark还可以很好的融入Hadoop的体系结构中可以直接操作HDFS，并提供Hive on Spark、Pig on Spark的框架集成Hadoop。

   * 兼容性

     Spark可以非常方便地与其他的开源产品进行融合。比如，Spark可以使用Hadoop的YARN和Apache Mesos作为它的资源管理和调度器，器，并且可以处理所有Hadoop支持的数据，包括HDFS、HBase和Cassandra等。这对于已经部署Hadoop集群的用户特别重要，因为不需要做任何数据迁移就可以使用Spark的强大处理能力。Spark也可以不依赖于第三方的资源管理和调度器，它实现了Standalone作为其内置的资源管理和调度框架，这样进一步降低了Spark的使用门槛，使得所有人都可以非常容易地部署和使用Spark。此外，Spark还提供了在EC2上部署Standalone的Spark集群的工具。

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/everywhere.png)

### （二）Spark的体系结构与安装部署

1. Spark集群的体系结构

   * 官方的一张图：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/arc1.png)

   * 容易理解的一张图：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/arc2.png)

2. Spark的安装与部署

   * Spark 的安装部署方式有以下几种模式：
     * Standalone
     * Yarn
     * Apache Mesos
     * Amazon EC2

   * Spark Standalone 伪分布的部署：

     * 配置文件：`conf/spark-env.sh`
       * export JAVA_HOME=/root/training/jdk1.7.0_75
       * export SPARK_MASTER_HOST=spark81
       * export SPARK_MASTER_PORT=7077
     * 配置文件：`conf/slave`
       * spark81

   * Spark Standalone全分布的部署：

     * 配置文件：`conf/spark-env.sh`
       * export JAVA_HOME=/root/training/jdk1.7.0_75
       * export SPARK_MASTER_HOST=spark81
       * export SPARK_MASTER_PORT=7077
     * 配置文件：`conf/slave`
       * spark83
       * spark84

   * 启动 Spark 集群：`start-all.sh`

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/startall.png)

3. Saprk HA的实现

   * 基于文件系统的单点恢复

     主要用于开发或测试环境。当 Spark 提供目录保存 spark Application 和 worker的注册信息，并将他们的恢复状态写入该目录中，这时，一旦 master 发生故障，就可以通过重新启动Master进程 `sbin/start-master.sh`，恢复已运行的 spark Application 和 worker 的注册信息。

     基于文件系统的单点恢复，主要是在 spark-env.sh 里对 SPARK_DAEMON_JAVA_OPTS 设置：

     |           配置参数           |                      参考值                      |
     | :--------------------------: | :----------------------------------------------: |
     |  spark.deploy.recoveryMode   | 设置为 FILESYSTEM 开启单点恢复功能，默认值：NONE |
     | spark.deploy.recoryDirectory |             Spark 保存恢复状态的目录             |

     参考：

     ```shell
     export SPARK_DAEMON_JAVA_OPTS="-Dspark.deploy.recoveryMode=FILESYSTEM -Dspark.recoveryDirectory=/root/training/spark-2.1.0-bin-hadoop2.7/recovery"
     ```

     测试：

     1. 在 spark82 上启动 Spark 集群

     2. 在 spark83 上启动 spark shell

        `MASTER=spark://spark82:7077 spark-shell`

     3. 在 spark82 上停止 master

        `stop-master.sh`

     4. 观察 spark83 上的输出：

        ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/spark83.png)

     5. 在 spark82 上重启 master：

        `start-master.sh`

   * 基于 Zookeeper 的 standBy Masters

     ZooKeeper提供了一个Leader Election机制，利用这个机制可以保证虽然集群存在多个Master，但是只有一个是Active的，其他的都是Standby。当Active的Master出现故障时，另外的一个Standby Master会被选举出来。由于集群的信息，包括Worker， Driver和Application的信息都已经持久化到ZooKeeper，因此在切换的过程中只会影响新Job的提交，对于正在进行的Job没有任何的影响。加入ZooKeeper的集群整体架构如下图所示：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/standalone.png)

     |          配置参数          |                    参考值                     |
     | :------------------------: | :-------------------------------------------: |
     | spark.deploy.recoveryMode  | 设置为ZOOKEEPER开启单点恢复功能，默认值：NONE |
     | spark.deploy.zookeeper.url |              ZooKeeper集群的地址              |
     | spark.deploy.zookeeper.dir |    Spark信息在ZK中的保存目录，默认：/spark    |

     参考：

     ```shell
     export SPARK_DAEMON_JAVA_OPTS="-Dspark.deploy.recoveryMode=ZOOKEEPER -Dspark.deploy.zookeeper.url=bigdata12:2181,bigdata13:2181,bigdata14:2181 -Dspark.deploy.zookeeper.dir=/spark"
     ```

     另外：每个节点需要将原来配置全分布环境的相关设置注释掉：

     ```powershell
     这两行注释掉
     
     # export SPARK_MASTER_HOST=spark82
     # export SPARK_MASTER_PORT=7077
     ```

     Zookeeper中保存的信息：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/sz.png)

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/active-standby.png)

### （三）执行Spark Demo程序

1. 执行 Spark Example 程序

   * 启动 spark 集群：`sbin/start-all.sh`

   * 实例程序：`$SPARK_HOME/examples/jars/spark-examples_2.11-2.1.0.jar`

   * 所有示例程序：`$SPARK_HOME/examples/src/main`，有 Java，python，Scala，R语言等各种版本

   * Demo：蒙特卡罗求π

     ```shell
     spark-submit --master spark://spark81:7077 org.apache.spark.examples.SparkPi examples/jars/spark-examples_2.11-2.1.0.jar 100
     ```

2. 使用 Spark Shell

   spark-shell是Spark自带的交互式Shell程序，方便用户进行交互式编程，用户可以在该命令行下用scala编写spark程序。

   * 启动 Spark Shell：`spark-shell`

     也可以使用以下参数：

     参数说明：

     ```shell
     --master spark://spark81:7077 # 指定 master 的地址
     --executor-memory 2g # 指定每个worker可用内存为2g
     --total-executor-cores 2 # 指定整个集群使用的cpu核数为2个
     ```

     例如：

     ```shell
     spark-shell --master spark://spark81:7077 --executor-memory 2g --total-executor-cores 2
     ```

   * 注意：

     如果启动spark shell时没有指定master地址，但是也可以正常启动spark shell和执行spark shell中的程序，其实是启动了spark的local模式，该模式仅在本机启动一个进程，没有与集群建立联系。

     请注意local模式和集群模式的日志区别：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/diff.png)

   * 在 Spark Shell 中编写 WordCount 程序

     程序如下：

     ```scala
     sc.textFile("hdfs://192.168.88.111:9000/data/data.txt")
       .flatMap(_.split(" "))
       .map((_, 1))
       .reduceByKey(_+_)
       .saveAsTextFile("hdfs://192.168.88.111:9000/output/spark/wc")
     ```

     说明：

     * `sc` 是 SparkContext 对象，该对象是提交 spark 程序的入口
     * `textFile("hdfs://192.168.88.111:9000/data/data.txt")` 是 hdfds 中读取数据
     * `flatMap(_.split(" "))` 先 map 在压平
     * `map((_, 1))` 将单词和 1 构成元组
     * `reduceByKey(_+_) ` 按照 key 进行 reduce，并将 value 累加
     * `saveAsTextFile("hdfs://192.168.88.111:9000/output/spark/wc") `  将结果写入到 hdfs 中 

3. 在IDEA 中编写 WordCount 程序

   * 所需的pom依赖：

     ```xml
     <dependency>
     	<groupId>org.apache.spark</groupId>
     	<artifactId>spark-core_2.11</artifactId>
     	<version>2.1.0</version>
     </dependency>
     ```

   * IDEA 安装 scala 插件

   * 创建maven工程

   * 书写源代码，并打成 jar 包，上传到 Linux

   * scala 版本：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/sca.png)

     运行程序：

     ```shell
     spark-submit --master spark://spark81:7077 --class mydemo.WordCount jars/wc.jar hdfs://192.168.88.111:9000/data/data.txt hdfs://192.168.88.111:9000/output/spark/wc
     ```

   * Java 版本（直接输出在屏幕上）：

     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/jav.png)

     运行程序：

     ```shell
     spark-submit --master spark://spark81:7077 --class mydemo.JavaWordCount jars/wc.jar hdfs://192.168.88.111:9000/data/data.txt
     ```

### （四）Spark运行机制及原理分析

1. WordCount 执行的流程分析

   ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/wc.png)

2. Spark 提交任务的流程

   ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/29-SparkCore/imgs/proc.png)

### （五）Spark的算子

1. RDD 基础
2. Transformation 算子
3. Action 算子
4. RDD 的缓存机制
5. RDD 的容错机制
6. RDD 的依赖关系和 Spark 任务中的 Stage
7. RDD 基础练习

### （六）Spark RDD的高级算子







### （七）Spark基础编程案例





### （八）一些问题

1. 自定义的Spark累加器，在foreach算子累加之值后，出了foreach算子累加的值消失？
  
   原因：重写的merge函数出错，导致Driver端在合并各个节点发来的累加器时未合并成功。
   
   * 错误的写法：
     
     ```scala
     override def merge(other: AccumulatorV2[String, mutable.HashMap[String, Int]]): Unit = {
         other match {
             case acc: SessionAggrStatAccumulator => {
                 // 将acc中的k-v对和当前map中的k-v对合并累加
                 this.aggrStatmap./:(acc.value) {
                     case (map, (k, v)) =>
                         map += (k -> (v + map.getOrElse(k, 0)))
                 }
             }
         }
     }
     ```
   
   * 正确的写法
     
     ```scala
     override def merge(other: AccumulatorV2[String, mutable.HashMap[String, Int]]): Unit = {
         other match {
             case acc: SessionAggrStatAccumulator => {
                 // 将acc中的k-v对和当前map中的k-v对合并累加
                 (this.aggrStatmap /: acc.value) {
                     case (map, (k, v)) =>
                         map += (k -> (v + map.getOrElse(k, 0)))
                 }
             }
         }
     }
     ```
   
   * 进一步探究出错的原因：scala的`/:`符号没有搞明白如何使用：
   
   * 错误的写法：通过点调用 `/:`会返回一个新的map，新map中的值进行了累加，而原来的两个 map 里的值均没有发生变化，相当于做了如下操作：`a + b`
   
   * 正确的写法：不通过点调用，而通过空格的方式调用相当于做了这个操作：`a = a + b`
   
   * 以此可以猜测 scala 的其他符号：`++``::``:::`等都有类似的性质。
