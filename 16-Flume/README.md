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

```

#### 案例三：监听某个目录，每当目录下新增文件时，将文件复制到HDFS上指定目录


```shell

```

#### 案例四：监听某个目录，每当目录下新增文件时，





