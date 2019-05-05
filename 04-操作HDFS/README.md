## HDFS

### （一）HDFS的命令行操作

#### 1. HDFS操作命令（HDFS操作命令帮助信息：hdfs dfs）

命令 | 说明 | 示例
---|---|---
-mkdir | 在HDFS上创建目录 | 在HDFS上创建目录/data: hdfs dfs -mkdir /data <br/> 在HDFS上级联创建目录/data/input: hdfs dfs -mkdir -p /data/input
-ls | 列出hdfs文件系统根目录下的目录和文件 | 查看HDFS根目录下的文件和目录: hdfs dfs -ls / <br/> 查看HDFS的/data目录下文件和目录: hdfs dfs -ls /data
-ls -R | 列出hdfs文件系统所有的目录和文件 | 查看HDFS根目录及其子目录下的文件和目录: hdfs dfs -ls -R /
-put | 上传文件或者从键盘输入字符到HDFS | 将本地Linux的文件data.txt上传到HDFS: hdfs dfs -put data.txt /data/input <br/> 从键盘输入字符保存到HDFS的文件: hdfs dfs -put - /aaa.txt (按Ctrl+c结束输入)
-moveFromLocal | 与put相类似，命令执行后源文件将从本地被移除 | hdfs dfs -moveFromLocal data.txt /data/input
-copyFromLocal | 与put相类似 | hdfs dfs -copyFromLocal data.txt /data/input
-get | 将HDFS中的文件复制到本地 | hdfs dfs -get /data/input/data.txt /root/
-rm | 每次可以删除多个文件或目录 | 删除多个文件: hdfs dfs -rm /data1.txt /data2.txt <br/> 删除多个目录: hdfs dfs -rm -r /data /input
-getmerge | 将hdfs指定目录下所有文件排序后合并到local指定的文件中，文件不存在时会自动创建，文件存在时会覆盖里面的内容 | 将HDFS上/data/input目录下的所有文件合并到本地的a.txt文件中: hdfs dfs -getmerge /data/input /root/a.txt
-cp | 在HDFS上拷贝文件 | 
-mv | 在HDFS上移动文件 | 
-count | 统计hdfs对应路径下的目录个数，文件个数，文件总计大小 <br/> 显示为目录个数，文件个数，文件总计大小，输入路径 | hdfs dfs -count /data
-du | 显示hdfs对应路径下每个文件和文件大小 | hdfs dfs -du /
-text、-cat | 相当于Linux的cat命令 | hdfs dfs -cat /input/1.txt
balancer | 如果管理员发现某些DataNode保存数据过多，某些DataNode保存数据相对较少，可以使用上述命令手动启动内部的均衡过程 | hdfs balancer

#### 2. HDFS管理命令（HDFS管理命令帮助信息：hdfs dfsadmin）

命令 | 说明 | 示例
---|---|---
-report | 显示HDFS的总容量，剩余容量，datanode的相关信息 | hdfs dfsadmin -report
-safemode | HDFS的安全模式命令 enter, leave, get, wait | hdfs dfsadmin -safemode enter <br/> hdfs dfsadmin -safemode leave <br/> hdfs dfsadmin -safemode get <br/> hdfs dfsadmin -safemode wait

### （二）HDFS的JavaAPI

所需pom依赖如下：
```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>3.8.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-client</artifactId>
        <version>2.7.3</version>
    </dependency>
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-common</artifactId>
        <version>2.7.3</version>
    </dependency>
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-hdfs</artifactId>
        <version>2.7.3</version>
    </dependency>
</dependencies>
```

通过HDFS提供的JavaAPI，我们可以完成以下的功能：

1. 在HDFS上创建目录
    ```java
    @Test
    public void testMkDir() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");
        FileSystem fs = FileSystem.get(conf);
        // 创建目录
        boolean flag = fs.mkdirs(new Path("/inputdata"));
        System.out.println(flag);
    }
    ```

1. 通过FileSystemAPI读取数据（下载文件）
    ```java
    @Test
    publci void testDownload() throws Exception {
        // 构造一个输入流 <-------HDFS
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.2.123:9000"), new Configuration());
        InputStream in = fs.open(new Path("/inputdata/a.war"));

        // 构造一个输出流
        OutputStream out = new FileOutputStream("d:\\a.war");
        IOUtils.copy(in, out);
    }
    ```

2. 写入数据（上传文件）
    ```java
    @Test
    public void testUpload() throws Exception {
        // 指定上传的文件（输入流）
        InputStream in = new FileInputStream("d:\\test.war");

        // 构造输出流 ----> HDFS
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.2.123:9000"), new Configuration());

        // 工具类 ---> 直接实现上传和下载
        IOUtils.copy(in, out);
    }
    ```

3. 查看目录及文件信息
    ```java
    @Test
    public void checkFileInformation() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        FileSystem fs = FileSystem.get(conf);
        FileStatus[] status = fs.listStatus(new Path("/hbase"));

        for (FileStatus f : status) {
            String dir = f.isDirectory() ? "目录" : "文件";
            String name = f.getPath().getName();
            String path = f.getPath().toString();
            System.out.println(dir + "------" + name + ",path:" + path);
            System.out.println(f.getAccessTime());
            System.out.println(f.getBlockSize());
            System.out.println(f.getGroup());
            System.out.println(f.getLen());
            System.out.println(f.getModificationTime());
            System.out.println(f.getOwner());
            System.out.println(f.getPermission());
            System.out.println(f.getReplication());
        }

    }
    ```

4. 查找某个文件在HDFS集群的位置
    ```java
    @Test
    public void findFileBlockLocation() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        FileSystem fs = FileSystem.get(conf);
        FileStatus fStatus = fs.getFileStatus(new Path("/data/mydata.txt"));

        BlockLocation[] blocks = fs.getFileBlockLocations(fStatus, 0, fStatus.getLen());
        for (BlockLocation block : blocks) {
            System.out.println(Arrays.toString(block.getHosts()) + "\t" + Arrays.toString(block.getNames()));
        }
    }
    ```

5. 删除数据
    ```java
    @Test
    public void deleteFile() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        FileSytem fs = FileSystem.get(conf);
        // 第二个参数表示是否递归
        boolean flag = fs.delete(new Path("/mydir/test.txt", false));
        System.out.println(flag ? "删除成功" : "删除失败");
    }
    ```

6. 获取HDFS集群上所有数据节点的信息
    ```java
    @Test
    public void testDataNode() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.137.25:9000");

        DistributedFileSystem fs = (DistributedFileSystem) FileSystem.get(conf);
        DatanodeInfo[] dataNodeStats = fs.getDataNodeStats();
        for (DatanodeInfo dataNode : dataNodeStats) {
            System.out.println(dataNode.getHostName() + "\t" + dataNode.getName());
        }
    }
    ```

### （三）HDFS的WebConsole

### （四）HDFS的回收站

### （五）HDFS的快照

### （六）HDFS的用户权限管理

### （七）HDFS的配额管理

### （八）HDFS的安全模式

### （九）HDFS的底层原理





