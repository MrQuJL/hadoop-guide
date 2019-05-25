## Storm

### （一）什么是Storm？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-log.png)

Storm为分布式实时计算提供了一组通用原语，可被用于“流处理”之中，实时处理消息并更新数据库。这是管理队列及工作者集群的另一种方式。 Storm也可被用于“连续计算”（continuous computation），对数据流做连续查询，在计算时就将结果以流的形式输出给用户。它还可被用于“分布式RPC”，以并行的方式运行昂贵的运算。 

Storm可以方便地在一个计算机集群中编写与扩展复杂的实时计算，Storm用于实时处理，就好比 Hadoop 用于批处理。Storm保证每个消息都会得到处理，而且它很快——在一个小集群中，每秒可以处理数以百万计的消息。更棒的是你可以使用任意编程语言来做开发。

### （二）离线计算和流式计算

#### 离线计算

* 离线计算：批量获取数据、批量传输数据、周期性批量计算数据、数据展示

* 代表技术：Sqoop批量导入数据、HDFS批量存储数据、MapReduce批量计算、Hive

#### 流式计算

* 流式计算：数据实时产生、数据实时传输、数据实时计算、实时展示

* 代表技术：Flume实时获取数据、Kafka/metaq实时数据存储、Storm/JStorm/SparkStreaming/Flink实时数据计算、Redis实时结果缓存、持久化存储(mysql)。

* 一句话总结：将源源不断产生的数据实时收集并实时计算，尽可能快的得到计算结果

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/water.png)

#### Storm与Hadoop的区别

Storm | Hadoop
---|---
Storm用于实时计算 | Hadoop用于离线计算
Storm处理的数据保存在内存中，源源不断 | Hadoop处理的数据保存在文件系统中，一批一批
Storm的数据通过网络传输进来 | Hadoop的数据保存在磁盘中
Storm与Hadoop的编程模型相似 | Storm与Hadoop的编程模型相似

### （三）Storm体系结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/arc1.png)

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/arc2.png)

* **Nimbus**：负责资源分配和任务调度
* **Supervisor**：负责接收 nimbus 分配的任务，启动和停止属于自己管理的 worker 进程。通过配置文件设置当前 supervisor 上启动多少个 worker。
* **Worker**：处理具体业务逻辑的进程。Worker 运行的任务类型只有两种，一种是 Spout 任务，一种是 Bolt 任务。
* **Executor**：Storm 0.8 之后，Executor 为 Worker 进程中具体的物理线程，同一个 Spout / Bolt 的 Task 可能会共享一个物理线程，一个 Executor 中只能运行隶属于同一个 Spout / Bolt 的 Task。
* **Task**：worker 中每一个 Spout / Bolt 的线程称为一个 task。在 Storm0.8 之后， Task 不再与物理线程对应，不同 Spout / Bolt 的 task 可能会共享一个物理线程，该线程称为 Executor。

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/worker-process.png)

### （四）Storm的运行机制

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/nimbus-process.png)

* 整个处理流程的组织协调不用用户去关心，用户只需要去定义每一个步骤中的具体业务处理逻辑。
* 具体执行任务的角色是 Worker，Worker执行任务时具体的行为则有我们定义的业务逻辑决定。

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-arc.png)

### （五）Storm的安装配置

* 下载 Storm：http://storm.apache.org/downloads.html

* 解压：```tar -zxvf apache-storm-1.0.3.tar.gz```

* 修改 ```/etc/profile``` 文件，设置环境变量：
    ```shell
    STORM_HOME=/root/training/apache-storm-1.0.3
    export STORM_HOME

    PATH=$STORM_HOME/bin:$PATH
    export PATH
    ```

* 编辑配置文件：$STORM_HOME/conf/storm.yaml

    ```shell
    ########### These MUST be filled in for a storm configuration
    # 配置zookeeper的地址
    storm.zookeeper.servers:
        - "192.168.137.81"
        - "192.168.137.82"
        - "192.168.137.83"

    # 配置storm主节点的地址
    nimbus.seeds: ["192.168.137.81"]

    # 配置storm存储数据的目录
    storm.local.dir: "/root/training/apache-storm-1.0.3/tmp"

    # 配置每个supervisor的worker的数目
    supervisor.slots.ports:
        - 6700
        - 6701
        - 6702
        - 6703
    ```

	> PS：如果要搭建 Storm 的 HA，只需要在 nimbus.seeds 中设置多个 nimbus 即可。

* 执行 ```scp``` 命令把安装包复制到其他节点上：

    ```shell
    scp -r apache-storm-1.0.3 root@192.168.137.82:/root/training
    scp -r apache-storm-1.0.3 root@192.168.137.83:/root/training
    ```

### （六）启动和查看Storm

* 在 nimbus.host 所属的机器上启动 nimbus 服务和 logviewer 服务
    ```shell
    nohup storm nimbus &
    nohup storm logviewer &
    ```

* 在 nimbus.host 所属的机器上启动 ui 服务
    ```shell
    nohup storm ui &
    ```

* 在其他节点上启动 supervisor 服务和 logviewer 服务
    ```shell
    nohup storm supervisor &
    nohup storm logviewer &
    ```

* 查看 storm 集群：访问 nimbus.host:/8080，即可看到 storm 的 ui 界面

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-ui.png)

### （七）Storm 编程模型

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-model.png)

* Topology：Storm 中运行的一个实时应用程序的名称。
* Spout：在一个 topology 中获取数据源流的组件。同常情况下，Spout 会从外部数据源中读取数据，然后转换为 topology 内部的源数据。
* Bolt：接收数据然后执行业务逻辑的组件，用户可以在其中执行自己想要的操作。
* Tuple：一次消息传递的基本单元，理解为一组消息就是一个 Tuple。
* Stream：表示数据的流向。
* StreamGroup：数据的分组策略。
	* Shuffle Grouping：随机分组，尽量均匀分布到下游 Bolt 中。
	* Fields Grouping：按字段分组，按数据中 field 值进行分组；相同 field 值的 Tuple 被发送到相同的 Task。
	* All Grouping：广播。
	* Global Grouping：全局分组，Tuple 被分配到一个 Bolt 中的一个 Task，实现事务性的 Topology。
	* None Grouping：不分组。
	* Direct Grouping：直接分组，指定分组。

### （八）Storm 编程案例：WordCount

流式计算一般架构图：

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/21-Storm基础/imgs/storm-wordcount.png)

* Flume 用来采集数据。
* Kafka 用来缓存 Flume 采集的数据。
* Storm 用来计算数据。
* Redis 是个内存数据库，用来保存数据。

* 所需 pom 依赖：
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.apache.storm</groupId>
            <artifactId>storm-core</artifactId>
            <version>1.0.3</version>
        </dependency>
    </dependencies>
    ```

* 创建 Spout 组件采集数据，作为整个 Topology 的数据源：
    ```java
    package test;

    import java.util.Map;
    import java.util.Random;

    import org.apache.storm.spout.SpoutOutputCollector;
    import org.apache.storm.task.TopologyContext;
    import org.apache.storm.topology.OutputFieldsDeclarer;
    import org.apache.storm.topology.base.BaseRichSpout;
    import org.apache.storm.tuple.Fields;
    import org.apache.storm.tuple.Values;
    import org.apache.storm.utils.Utils;

    public class WordCountSpout extends BaseRichSpout {

        // 模拟数据
        private String[] data = {"I love Beijing", "I love China", "Beijing is the capital of China"};

        // 用于往下一个组件发送消息
        private SpoutOutputCollector collector;

        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            // spout初始化方法
            this.collector = collector;
        }

        // 该方法由Storm框架调用，用于接收外部数据源的数据
        public void nextTuple() {
            Utils.sleep(3000);
            int random = (new Random()).nextInt(3);
            String sentence = data[random];

            // 发送数据
            System.out.println("发送数据：" + sentence);
            this.collector.emit(new Values(sentence));
        }

        // 声明输出数据的key
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("sentence"));

        }
    }
    ```

* 创建Bolt（WordCountSplitBolt）组件进行分词操作
    ```java
    package test;

    import java.util.Map;

    import org.apache.storm.task.OutputCollector;
    import org.apache.storm.task.TopologyContext;
    import org.apache.storm.topology.OutputFieldsDeclarer;
    import org.apache.storm.topology.base.BaseRichBolt;
    import org.apache.storm.tuple.Fields;
    import org.apache.storm.tuple.Tuple;
    import org.apache.storm.tuple.Values;

    public class WordCountSplitBolt extends BaseRichBolt {

        // 向下一级Bolt组件发送数据
        private OutputCollector collector;

        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        public void execute(Tuple input) {
            String sentence = input.getStringByField("sentence");
            // 分词
            String[] words = sentence.split(" ");
            for (String word : words) {
                this.collector.emit(new Values(word, 1));
            }
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }
    ```

* 创建Bolt（WordCountBoltCount）组件进行单词计数作
    ```java
    package test;

    import java.util.HashMap;
    import java.util.Map;

    import org.apache.storm.task.OutputCollector;
    import org.apache.storm.task.TopologyContext;
    import org.apache.storm.topology.OutputFieldsDeclarer;
    import org.apache.storm.topology.base.BaseRichBolt;
    import org.apache.storm.tuple.Tuple;

    public class WordCountBoltCount extends BaseRichBolt {

        private Map<String, Integer> result = new HashMap<String, Integer>();

        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

        }

        public void execute(Tuple input) {
            String word = input.getStringByField("word");
            int count = input.getIntegerByField("count");

            if (result.containsKey(word)) {
                int total = result.get(word);
                result.put(word, total + count);
            } else {
                result.put(word, 1);
            }
            // 直接输出到屏幕
            System.out.println("输出的结果是：" + result);
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {

        }
    }
    ```

* 创建主程序Topology（WordCountTopology），并提交到本地运行
    ```java
    package test;

    import org.apache.storm.Config;
    import org.apache.storm.LocalCluster;
    import org.apache.storm.StormSubmitter;
    import org.apache.storm.generated.StormTopology;
    import org.apache.storm.topology.TopologyBuilder;
    import org.apache.storm.tuple.Fields;

    public class WordCountTopology {

        public static void main(String[] args) throws Exception {
            TopologyBuilder builder = new TopologyBuilder();

            // 设置任务的spout组件
            builder.setSpout("wordcount_spout", new WordCountSpout());

            // 设置任务的第一个bolt组件
            builder.setBolt("wordcount_splitbolt", new WordCountSplitBolt()).shuffleGrouping("wordcount_spout");

            // 设置任务的第二个Bolt组件
            builder.setBolt("wordcount_count", new WordCountBoltCount()).fieldsGrouping("wordcount_splitbolt", new Fields("word"));

            // 创建Topology任务
            StormTopology wc = builder.createTopology();

            Config config = new Config();

            // 提交任务到本地运行
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("mywordcount", config, wc);

            // 提交任务到storm集群上运行
    //		StormSubmitter.submitTopology(args[0], config, wc);
        }
    }
    ```

* 在 Eclipse 上右击运行即可（**注：要以管理员方式启动Eclipse**）。






















