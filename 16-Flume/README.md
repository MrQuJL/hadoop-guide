## Flume

### （一）什么是Flume？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/16-Flume/imgs/flume-logo.png)

Flume是Cloudera提供的一个高可用的，高可靠的，分布式的海量日志采集、聚合和传输的系统，Flume支持在日志系统中定制各类数据发送方，用于收集数据；同时，Flume提供对数据进行简单处理，并写到各种数据接受方（可定制）的能力。


### （二）Flume的体系结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/16-Flume/imgs/flume-arc.png)

* Source: 用于采集日志数据

* Channel: 缓存日志数据

* Sink: 将Channel中缓存的日志数据转移到指定地点(此处是HDFS)

### （三）安装和配置Flume

* 下载Flume：http://flume.apache.org/download.html

* 解压：tar -zxvf apache-flume-1.7.0-bin.tar.gz -C ~/training

* 将flume-env.sh.template改名为flume-env.sh

* 修改conf/flume-env.sh设置JAVA_HOME即可

### （四）使用Flume采集日志数据

在Flume根目录下新建一个myagent目录，在myagent目录下创建如下的配置文件：

#### 案例一：监听某个文件的末尾，将新增内容打印到控制台

```shell
#bin/flume-ng agent -n a1 -f myagent/a1.conf -c conf -Dflume.root.logger=INFO,console
#定义agent名，source、channel、sink的名称
a1.sources=r1
a1.channels=c1
a1.sinks=k1

#具体定义source
a1.sources.r1.type=exec
a1.sources.r1.command=tail -F /root/logs/a.log

#具体定义channel
a1.channels.c1.type=memory
a1.channels.c1.capacity=1000
a1.channels.c1.transactionCapacity=100

#具体定义sink
a1.sinks.k1.type=logger

#组装source,channel,sink
a1.sources.r1.chanels=c1
a1.sinks.k1.channel=c1
```

#### 案例二：监听某个目录，每当目录下新增文件时，将该文件的内容打印到控制台

```shell
#bin/flume-ng agent -n a2 -f myagent/a2.conf -c conf -Dflume.root.logger=INFO,console
#定义agent名，source，channel，sink的名称
a2.sources=r1
a2.channels=c1
a2.sinks=k1

#具体定义source
a2.sources.r1.type=spooldir
a2.sources.r1.spoolDir=/root/logs/

#具体定义channel
a2.channels.c1.type=memory
a2.channels.c1.capacity=1000
a2.channels.c1.transactionCapacity=100

#具体定义sink
a2.sinks.k1.type=logger

#组装source，channel，sinks
a2.sources.r1.channels=c1
a2.sinks.k1.channel=c1
```

#### 案例三：监听某个目录，每当目录下新增文件时，将文件复制到HDFS上指定目录

```shell
#bin/flume-ng agent -n a3 -f myagent/a3.conf -c conf -Dflume.root.logger=INFO,console
#定义agent名，source，channel，sink的名称
a3.sources=r1
a3.channels=c1
a3.sinks=k1

#具体定义source
a3.sources.r1.type=spooldir
a3.sources.r1.spoolDir=/root/logs

#为source定义拦截器，给消息添加时间戳
a3.sources.r1.interceptors=i1
a3.sources.r1.interceptors.i1.type=org.apache.flume.interceptor.TimestampInterceptor$Builder

#具体定义channel
a3.channels.c1.type=memory
a3.channels.c1.capacity=1000
a3.channels.c1.transactionCapacity=100

#具体定义sink
a3.sinks.k1.type=hdfs
a3.sinks.k1.hdfs.path=hdfs://127.0.0.1:9000/flume/%Y%m%d
a3.sinks.k1.hdfs.filePrefix=events-
a3.sinks.k1.hdfs.fileType=DataStream

#不按照条数生成文件
a3.sinks.k1.hdfs.rollCount=0
a3.sinks.k1.hdfs.rollSize=134217728
a3.sinks.k1.hdfs.rollInterval=60

#组装source，channel，sink
a3.sources.r1.channels=c1
a3.sinks.k1.channel=c1
```

#### 案例四：监听某个目录，每当目录下新增文件时，将数据推送到kafka

```shell
#bin/flume-ng agent -n a4 -f myagent/a4.conf -c conf -Dflume.root.logger=INFO,console
#定义a4名， source、channel、sink的名称
a4.sources = r1
a4.channels = c1
a4.sinks = k1

#具体定义source
a4.sources.r1.type = spooldir
a4.sources.r1.spoolDir = /root/logs

#具体定义channel
a4.channels.c1.type = memory
a4.channels.c1.capacity = 10000
a4.channels.c1.transactionCapacity = 100

#设置Kafka接收器
a4.sinks.k1.type= org.apache.flume.sink.kafka.KafkaSink

#设置Kafka的broker地址和端口号
#HDP 集群kafka broker的默认端口是6667，而不是9092
a4.sinks.k1.brokerList=qujianlei:9092

#设置Kafka的Topic
a4.sinks.k1.topic=mytopic

#设置序列化方式
a4.sinks.k1.serializer.class=kafka.serializer.StringEncoder

#组装source、channel、sink
a4.sources.r1.channels = c1
a4.sinks.k1.channel = c1
```

### 常见问题

1. 内存不足导致flume进程经常死掉

	修改$FLUME_HOME/bin/flume-ng中JAVA_OPTS变量-Xmx的值

2. 采集kafka数据或者生产kafka数据的时候默认数据大小是1M，所以使用flume采集kafka数据或向kafka送数据的时候需要向agent配置文件中添加相应的参数
	* 采集source：```agent.sources.r1.kafka.consumer.max.partition.fetch.bytes=10240000```
	* 发送sink：```agent.sinks.k1.kafka.producer.max.request.size=10240000```





### 其他

* 安装netcat教程：https://blog.csdn.net/z1941563559/article/details/81347981



