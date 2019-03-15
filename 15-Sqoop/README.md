## Sqoop

### （一）什么是Sqoop？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/15-Sqoop/imgs/sqoop-logo.png)


Sqoop是一款开源的工具，主要用于在Hadoop(Hive)与传统的数据库(mysql、postgresql...)间进行数据的传递，可以将一个关系型数据库（例如 ： MySQL ,Oracle ,Postgres等）中的数据导进到Hadoop的HDFS中，也可以将HDFS的数据导进到关系型数据库中。

Sqoop项目开始于2009年，最早是作为Hadoop的一个第三方模块存在，后来为了让使用者能够快速部署，也为了让开发人员能够更快速的迭代开发，Sqoop独立成为一个Apache项目。

### （二）Sqoop是如何工作？

* 底层就是利用JDBC连接数据库。

### （三）安装配置Sqoop

* sqoop下载地址：http://mirror.bit.edu.cn/apache/sqoop/1.4.7/

* 解压tar包到指定目录

* 添加Sqoop根目录到环境变量

* 将mysql的mysql-connector-java-5.1.43-bin.jar包放到sqoop的lib目录下

### （四）使用Sqoop

命令 | 说明
---|---
codegen | 将关系数据库表映射为一个Java文件、Java class类、以及相关的jar包
create-hive-table | 生成与关系数据库表的表结构对应的HIVE表
eval | 以快速地使用SQL语句对关系数据库进行操作，这可以使得在使用import这种工具进行数据导入的时候，可以预先了解相关的SQL语句是否正确，并能将结果显示在控制台。
export | 从hdfs中导数据到关系数据库中
help | 
import | 将数据库表的数据导入到HDFS中
import-all-tables | 将数据库中所有的表的数据导入到HDFS中
job | 用来生成一个sqoop的任务，生成后，该任务并不执行，除非使用命令执行该任务。
list-databases | 打印出关系数据库所有的数据库名
list-tables | 打印出关系数据库某一数据库的所有表名
merge | 将HDFS中不同目录下面的数据合在一起，并存放在指定的目录中
metastore | 记录sqoop job的元数据信息
version | 显示sqoop版本信息

### （五）案例

* 案例一：将mysql中的表映射为一个java文件

	```shell
	sqoop codegen --connect jdbc:mysql://localhost:3306/dbname --username root --password 123 --table emp
	```

* 案例二：根据mysql的表结构在hive中创建一个同样结构的表

	```shell
	sqoop create-hive-table --connect jdbc:mysql://localhost:3306/dbname --username root --password Welcome_1 --table emp --hive-table emp
	```

	> 注：需要将hive/lib中的hive-common-2.3.3.jar拷贝到sqoop的lib目录中，否则执行报错。

* 案例三：通过Sqoop验证一条SQL语句是否正确

	```shell
	sqoop eval --connect jdbc:mysql://localhost:3306/dbname --username root --password Welcome_1 --query 'select * from cate'
	```











