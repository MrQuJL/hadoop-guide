




### 一些问题

* 自定义的Spark累加器，在foreach算子累加之值后，出了foreach算子累加的值消失？

	原因：重写的merge函数出错，导致Driver端在合并各个节点发来的累加器时未合并成功。
	
	* 错误的写法：
	
	override def merge(other: AccumulatorV2[String, mutable.HashMap[String, Int]]): Unit = {
      other match {
        case acc:SessionAggrStatAccumulator => {
          // 将acc中的key-value对于当前map中的key-value对合并累加
          this.aggrStatMap./:(acc.value){
            case (map, (k, v)) => 
              map += (k -> (v + map.getOrElse(k, 0)))
          }
        }
      }
    }

	* 正确的写法：
	
	override def merge(other: AccumulatorV2[String, mutable.HashMap[String, Int]]): Unit = {
      other match {
        case acc:SessionAggrStatAccumulator => {
          // 将acc中的key-value对于当前map中的key-value对合并累加
          (this.aggrStatMap /: acc.value){ case (map, (k,v)) => map += ( k -> (v + map.getOrElse(k, 0)) )}
        }
      }
    }

	* 进一步探究出错的原因：scala的/:符号没有搞明白如何使用？
	
	* 出错的根本原因：对scala语言不熟悉。
	
	
	

