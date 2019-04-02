## HDFS

### （一）HDFS的命令行操作

#### 1. HDFS操作命令（HDFS操作命令帮助信息：hdfs dfs）

命令 | 说明 | 示例
---|---|---
-mkdir | 在HDFS上创建目录 | 在HDFS上创建目录/data: hdfs dfs -mkdir /data <br/> 在HDFS上级联创建目录/data/input: hdfs dfs -mkdir -p /data/input
-ls | 列出hdfs文件系统根目录下的目录和文件 | 查看HDFS根目录下的文件和目录: hdfs dfs -ls / <br/> 查看HDFS的/data目录下文件和目录: hdfs dfs -ls /data
-ls -R | 列出hdfs文件系统所有的目录和文件 | 查看HDFS根目录及其子目录下的文件和目录: hdfs dfs -ls -R /
-put | 上传文件或者从键盘输入字符到HDFS | 将本地Linux的文件data.txt上传到HDFS: hdfs dfs -put data.txt /data/input <br/> 从键盘输入字符保存到HDFS的文件: hdfs dfs -put - /aaa.txt

#### 2. HDFS管理命令（HDFS管理命令帮助信息：hdfs dfsadmin）




### （二）HDFS的JavaAPI

### （三）HDFS的WebConsole

### （四）HDFS的回收站

### （五）HDFS的快照

### （六）HDFS的用户权限管理

### （七）HDFS的配额管理

### （八）HDFS的安全模式

### （九）HDFS的底层原理





