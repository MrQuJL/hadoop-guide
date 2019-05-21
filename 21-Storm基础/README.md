## Storm

### （一）什么是Storm？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-log.png)

Storm为分布式实时计算提供了一组通用原语，可被用于“流处理”之中，实时处理消息并更新数据库。这是管理队列及工作者集群的另一种方式。 Storm也可被用于“连续计算”（continuous computation），对数据流做连续查询，在计算时就将结果以流的形式输出给用户。它还可被用于“分布式RPC”，以并行的方式运行昂贵的运算。 

Storm可以方便地在一个计算机集群中编写与扩展复杂的实时计算，Storm用于实时处理，就好比 Hadoop 用于批处理。Storm保证每个消息都会得到处理，而且它很快——在一个小集群中，每秒可以处理数以百万计的消息。更棒的是你可以使用任意编程语言来做开发。
