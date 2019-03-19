## Hadoop2.x的安装与配置

### （一）Hadoop安装部署的预备条件

* 安装Linux

* 安装JDK

### （二）Hadoop的目录结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/02-搭建Hadoop的环境/imgs/hadoop.png)

### （三）Hadoop安装部署的三种模式

* 本地模式

参数文件 | 配置参数 | 参考值
---|---|---
hadoop-env.sh | JAVA_HOME | /root/training/jdk1.8.0_144

* 伪分布模式

参数文件 | 配置参数 | 参考值
---|---|---
hadoop-env.sh | JAVA_HOME | /root/training/jdk1.8.0_144
hdfs-site.xml | dfs.replication | 1
... | dfs.permissions | false
core-site.xml | fs.defaultFS | hdfs://<hostname>:9000
... | hadoop.tmp.dir | /root/training/hadoop-2.7.3/tmp
mapred-site.xml | mapreduce.framework.name | yarn
yarn-site.xml | yarn.resourcemanager.hostname | <hostname>
... | yarn.nodemanager.aux-services | mapreduce_shuffle

* 全分布模式

参数文件 | 配置参数 | 参考值
---|---|---
hadoop-env.sh | JAVA_HOME | /root/training/jdk1.8.0_144
hdfs-site.xml | dfs.replication | 2
... | dfs.permissions | false
core-site.xml | fs.defaultFS | hdfs://<hostname>:9000
... | hadoop.tmp.dir | /root/training/hadoop-2.7.3/tmp
mapred-site.xml | mapreduce.framework.name | yarn
yarn-site.xml | yarn.resourcemanager.hostname | <hostname>
... | yarn.nodemanager.aux-services | mapreduce_shuffle
slaves | DataNode的ip地址或主机名 | qujianlei001






