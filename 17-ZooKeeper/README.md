## Zookeeper

### （一）什么是ZooKeeper？

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/17-ZooKeeper/imgs/zookeeper-logo.png)

ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实现，是Hadoop和Hbase的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。

### （二）ZooKeeper的体系结构

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/17-ZooKeeper/imgs/zookeeper-arc.png)

### （三）Zookeeper能帮我们做什么？

* Hadoop2.0使用Zookeeper来实现HA（高可用，有多个namenode），同时使用它的事件处理确保整个集群只有一个活跃的NameNode，存储配置信息等。

* HBase,使用Zookeeper的事件处理确保整个集群只有一个HMaster，察觉HRegionServer联机和宕机,存储访问控制列表等。

### （四）安装配置启动ZooKeeper

1. 下载：https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/

2. 解压：tar -zxvf zookeeper-3.4.14.tar.gz

3. 修改```$ZOOKEEPER_HOME/conf/zoo.cfg```内容如下：
    ```shell
    dataDir=$ZOOKEEPER_HOME/tmp

    server.1=hostname1:2888:3888
    server.2=hostname2:2888:3888
    server.3=hostname3:2888:3888
    ```

	> 2888端口是用来在多个zookeeper节点之间进行数据同步
	> 3888端口是当leader死掉了，来选举一个新leader

4. 在```$ZOOKEEPER_HOME/tmp```目录下创建一个myid的空文件，执行如下命令：
    ```shell
    echo 1 > $ZOOKEEPER_HOME/tmp/myid
    ```

5. 将配置好的zookeeper拷贝到其他的机器上，同时修改各自的myid文件为2,3：
    ```shell
    scp -r $ZOOKEEPER_HOME/ hostname2:/$ZOOKEEPER_HOME
    scp -r $ZOOKEEPER_HOME/ hostname3:/$ZOOKEEPER_HOME
    ```

6. 启动Zookeeper：```zkServer.sh start```

7. 查看Zookeeper状态：```zkServer.sh status```

### （五）Zookeeper常用命令

1. ls -- 查看某个目录包含的所有文件，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] ls /
    ```

2. ls2 -- 与ls类似，不同的是它可以看到time、version等信息，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] ls2 /
    ```

3. create -- 创建znode，并设置初始内容，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] create /test "test" 
    ```

4. get -- 获取znode的数据，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] get /test
    ```

5. set -- 修改znode的内容，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] set /test "ricky"
    ```

6. delete -- 删除znode，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] delete /test
    ```

7. quit -- 退出客户端，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] quit
    ```

8. help -- 帮助命令，例如：
    ```shell
    [zk: 127.0.0.1:2181(CONNECTED) 1] help
    ```

### （六）Zookeeper应用场景

* 利用zookeeper的分布式锁实现秒杀：
    pom依赖：
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-client</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>16.0.1</version>
        </dependency>
    </dependencies>
    ```

    主程序：
    ```java
    package com.qjl.kafkatest.producer;

    import org.apache.curator.RetryPolicy;
    import org.apache.curator.framework.CuratorFramework;
    import org.apache.curator.framework.CuratorFrameworkFactory;
    import org.apache.curator.framework.recipes.locks.InterProcessMutex;
    import org.apache.curator.retry.ExponentialBackoffRetry;

    public class TestDistributedLock {

        private static int number = 10;

        private static void getNumber() {
            System.out.println("\n\n******* 开始业务方法   ************");
            System.out.println("当前值：" + number);
            number--;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            // 最多失败重连10次,每次间隔1000ms
            RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
            CuratorFramework cf = CuratorFrameworkFactory.builder()
                    .connectString("qujianlei:2181")
                    .retryPolicy(policy)
                    .build();
            cf.start();

            final InterProcessMutex lock = new InterProcessMutex(cf, "/aaa");

            for (int i = 0; i < 10; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lock.acquire();
                            getNumber();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                lock.release();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();;
            }
        }
    }
    ```










