## Hadoop应用案例分析

### （一）互联网应用的架构

1. 传统的架构

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/tra.png)

2. 改良后的架构

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/optimize.png)

3. 完整的架构

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/full.png)

### （二）日志分析

1. 需求说明：

	对某技术论坛的apache server日志分析，计算论坛关键指标，供运营者决策。

2. 论坛日志数据有两部分：

	* 历史数据约56GB，统计到2012-05-29

	* 自2013-05-30起，每天生成一个数据文件，约150MB

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/log.png)

3. 关键指标：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/point.png)

4. 系统架构：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/arc.png)

5. 改良后的系统架构：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/oparc.png)

6. HBase表的结构：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/table.png)

7. 日志分析的执行过程：

	* 周期性把日志数据导入到hdfs中

	* 周期性把明细日志导入hbase存储

	* 周期性使用hive进行数据的多维分析

	* 周期性把hive分析结果导入到mysql中

### （三）Hadoop在淘宝的应用

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/03-Hadoop的应用案例分析/imgs/ali.png)
