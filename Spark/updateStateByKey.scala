package streamingoutput

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.hadoop.conf.Configuration
import org.apache.spark.storage.StorageLevel
import org.apache.log4j.Level
import org.apache.log4j.Logger

object MyCheckPointNetWorkWordCount {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "F:\\第七期\\hadoop-2.7.3\\hadoop-2.7.3");
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    
    val hadoopConf = new Configuration
    hadoopConf.set("dfs.client.use.datanode.hostname", "true")
    
	// 使用下面的这个API可以配置Hadoop的相关信息，比如：返回datanode的主机名，这样我们外网就可以访问HDFS所处的内网了，前提是需要修改本地hosts文件的映射
    // 通过StreamingContext直接创建一个StreamingContext对象
    val ssc = StreamingContext.getOrCreate("hdfs://xxxxxx:9000/checkpoint", creatingFunc, hadoopConf)
    
    
    val lines = ssc.socketTextStream("39.107.70.197", 1234, StorageLevel.MEMORY_AND_DISK_SER)
    
    // 处理数据
    val words = lines.flatMap(_.split(" "))
    val result = words.map(x => (x, 1)).updateStateByKey(accumulate)
    result.print()
    
    ssc.start()
    ssc.awaitTermination()
  }
  
  /**
   * 创建StreamingContext
   */
  def creatingFunc(): StreamingContext = {
    val conf = new SparkConf().setAppName("ckptWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))
    // 在这里设置检查点目录也可以
    ssc.checkpoint("hdfs://xxxxxx:9000/checkpoint")
    ssc
  }
  
  def accumulate(curValue: Seq[Int], prevValue: Option[Int]) = {
    val result = curValue.sum
    Some(result + prevValue.getOrElse(0))
  }
  
}