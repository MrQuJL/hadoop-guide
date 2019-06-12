## Spark Core

### （一）什么是Spark？

### （二）Spark的体系结构与安装部署

### （三）执行Spark Demo程序

### （四）Spark运行机制及原理分析

### （五）Spark的算子

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
   
   . 进一步探究出错的原因：scala的`/:`符号没有搞明白如何使用：
     
     . 错误的写法：通过点调用 `/:`会返回一个新的map，新map中的值进行了累加，而原来的两个 map 里的值均没有发生变化，相当于做了如下操作：`a + b`
     
     . 正确的写法：不通过点调用，而通过空格的方式调用相当于做了这个操作：`a = a + b`
     
     . 以此可以猜测 scala 的其他符号：`++``::``:::`等都有类似的性质。






































