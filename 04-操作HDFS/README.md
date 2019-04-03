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

### （三）HDFS的WebConsole

### （四）HDFS的回收站

### （五）HDFS的快照

### （六）HDFS的用户权限管理

### （七）HDFS的配额管理

### （八）HDFS的安全模式

### （九）HDFS的底层原理





