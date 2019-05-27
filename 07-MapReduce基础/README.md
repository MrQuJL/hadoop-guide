## MapReduce

### （一）MapReduce在Yarn平台上运行过程

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/07-MapReduce基础/imgs/mr-yarn.png)

### （二）第一个MapReduce程序：WordCount

* 所需的 pom 依赖：
    ```xml
    <dependencies>
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

* Mapper 实现：
    ```java
    import java.io.IOException;

    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.Text;
    import org.apache.hadoop.mapreduce.Mapper;

    public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        private Text k = new Text();

        private LongWritable v = new LongWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            // 分词
            String[] words = line.split(" ");
            // 输出
            for (String word : words) {
                k.set(word);
                v.set(1L);
                context.write(k, v);
            }
        }
    }
    ```


* Reducer 实现：
    ```java
    import java.io.IOException;

    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.Text;
    import org.apache.hadoop.mapreduce.Reducer;

    public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

        private LongWritable value = new LongWritable();

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            // 计数
            for (LongWritable v : values) {
                sum += v.get();
            }
            // 输出
            value.set(sum);
            context.write(key, value);
        }
    }
    ```

* Driver 实现：
    ```java
    package github;

    import org.apache.hadoop.conf.Configuration;
    import org.apache.hadoop.fs.Path;
    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.Text;
    import org.apache.hadoop.mapreduce.Job;
    import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
    import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

    public class Driver {

        public static void main(String[] args) throws Exception {
            args = new String[]{"D:/EclipseWorkspace/mapreducetop10/hello.txt",
                "D:/EclipseWorkspace/mapreducetop10/output"};

            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);
            // 指明程序的入口
            job.setJarByClass(Driver.class);

            // 指明mapper
            job.setMapperClass(WordCountMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(LongWritable.class);

            // 指明reducer
            job.setReducerClass(WordCountReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);

            // 指明任务的输入输出路径
            FileInputFormat.setInputPaths(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            // 启动任务
            job.waitForCompletion(true);
        }
    }
    ```

### （三）WordCount的数据流动过程

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/07-MapReduce基础/imgs/mr-dataflow.png)

### （四）使用MapReduce进行排序

**排序：注意排序按照 Key2（Mapper输出的key） 排序，key2 需要实现WritableComparable接口**

* 测试数据：emp.csv
    ```shell
    7369,SMITH,CLERK,7902,1980/12/17,800,,20
    7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
    7521,WARD,SALESMAN,7698,1981/2/22,1250,500,30
    7566,JONES,MANAGER,7839,1981/4/2,2975,,20
    7654,MARTIN,SALESMAN,7698,1981/9/28,1250,1400,30
    7698,BLAKE,MANAGER,7839,1981/5/1,2850,,30
    7782,CLARK,MANAGER,7839,1981/6/9,2450,,10
    7788,SCOTT,ANALYST,7566,1987/4/19,3000,,20
    7839,KING,PRESIDENT,,1981/11/17,5000,,10
    7844,TURNER,SALESMAN,7698,1981/9/8,1500,0,30
    7876,ADAMS,CLERK,7788,1987/5/23,1100,,20
    7900,JAMES,CLERK,7698,1981/12/3,950,,30
    7902,FORD,ANALYST,7566,1981/12/3,3000,,20
    7934,MILLER,CLERK,7782,1982/1/23,1300,,10
    ```

* SortMapper：
    ```java
    import java.io.IOException;

    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.NullWritable;
    import org.apache.hadoop.io.Text;
    import org.apache.hadoop.mapreduce.Mapper;
    import org.apache.hadoop.mapreduce.Mapper.Context;

    public class SortMapper extends Mapper<LongWritable, Text, Employee, NullWritable> {

        @Override
        protected void map(LongWritable key, Text value,Context context)
                throws IOException, InterruptedException {
            //7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
            String str = value.toString();
            //分词
            String[] words = str.split(",");

            Employee e = new Employee();
            e.setEmpno(Integer.parseInt(words[0]));
            e.setEname(words[1]);
            e.setJob(words[2]);
            try {
                e.setMgr(Integer.parseInt(words[3]));
            } catch (Exception e2) {
                e.setMgr(0);
            }
            e.setHiredate(words[4]);
            e.setSal(Integer.parseInt(words[5]));
            try {
                e.setComm(Integer.parseInt(words[6]));
            } catch (Exception e2) {
                e.setComm(0);
            }		
            e.setDeptno(Integer.parseInt(words[7]));

            //将这个员工输出
            context.write(e, NullWritable.get());
        }
    }
    ```

* 实现 WritableComparable 接口的 key2：
    ```java
    package demo.sort;

    import java.io.DataInput;
    import java.io.DataOutput;
    import java.io.IOException;

    import org.apache.hadoop.io.Writable;
    import org.apache.hadoop.io.WritableComparable;

    //7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
    public class Employee implements WritableComparable<Employee>{

        private int empno;
        private String ename;
        private String job;
        private int mgr;
        private String hiredate;
        private int sal;
        private int comm;
        private int deptno;

        public Employee(){

        }

        @Override
        public int compareTo(Employee o) {
            // 排序规则
            if(this.sal >= o.getSal()){
                return 1;
            }else{
                return -1;
            }
        }

        @Override
        public String toString() {
            return "Employee [empno=" + empno + ", ename=" + ename + ", job=" + job
                    + ", mgr=" + mgr + ", hiredate=" + hiredate + ", sal=" + sal
                    + ", comm=" + comm + ", deptno=" + deptno + "]";
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            this.empno = in.readInt();
            this.ename = in.readUTF();
            this.job = in.readUTF();
            this.mgr = in.readInt();
            this.hiredate = in.readUTF();
            this.sal = in.readInt();
            this.comm = in.readInt();
            this.deptno = in.readInt();
        }

        @Override
        public void write(DataOutput output) throws IOException {
            ////7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
            output.writeInt(empno);
            output.writeUTF(ename);
            output.writeUTF(job);
            output.writeInt(mgr);
            output.writeUTF(hiredate);
            output.writeInt(sal);
            output.writeInt(comm);
            output.writeInt(deptno);
        }

        public int getEmpno() {
            return empno;
        }

        public void setEmpno(int empno) {
            this.empno = empno;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getMgr() {
            return mgr;
        }

        public void setMgr(int mgr) {
            this.mgr = mgr;
        }

        public String getHiredate() {
            return hiredate;
        }

        public void setHiredate(String hiredate) {
            this.hiredate = hiredate;
        }

        public int getSal() {
            return sal;
        }

        public void setSal(int sal) {
            this.sal = sal;
        }

        public int getComm() {
            return comm;
        }

        public void setComm(int comm) {
            this.comm = comm;
        }

        public int getDeptno() {
            return deptno;
        }

        public void setDeptno(int deptno) {
            this.deptno = deptno;
        }
    }
    ```

* 驱动程序：
    ```java
    import org.apache.hadoop.conf.Configuration;
    import org.apache.hadoop.fs.Path;
    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.NullWritable;
    import org.apache.hadoop.io.Text;
    import org.apache.hadoop.mapreduce.Job;
    import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
    import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

    public class SortMain {

        public static void main(String[] args) throws Exception{

            // 求员工工资的总额
            Job job = new Job(new Configuration());

            //指明程序的入口
            job.setJarByClass(SortMain.class);

            //指明任务中的mapper
            job.setMapperClass(SortMapper.class);
            job.setMapOutputKeyClass(Employee.class);
            job.setMapOutputValueClass(NullWritable.class);

            job.setOutputKeyClass(Employee.class);
            job.setOutputValueClass(NullWritable.class);

            //指明任务的输入路径和输出路径	---> HDFS的路径
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //启动任务
            job.waitForCompletion(true);
        }
    }
    ```

### （五）使用Partitioner进行分区

* Mapper：
    ```java
    import java.io.IOException;

    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.NullWritable;
    import org.apache.hadoop.io.Text;
    import org.apache.hadoop.mapreduce.Mapper;
    import org.apache.hadoop.mapreduce.Mapper.Context;

    public class EmployeeMapper  extends Mapper<LongWritable, Text, LongWritable, Employee> {

        @Override
        protected void map(LongWritable key, Text value,Context context)
                throws IOException, InterruptedException {
            //7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
            String str = value.toString();
            //分词
            String[] words = str.split(",");

            Employee e = new Employee();
            e.setEmpno(Integer.parseInt(words[0]));
            e.setEname(words[1]);
            e.setJob(words[2]);
            try {
                e.setMgr(Integer.parseInt(words[3]));
            } catch (Exception e2) {
                e.setMgr(0);
            }
            e.setHiredate(words[4]);
            e.setSal(Integer.parseInt(words[5]));
            try {
                e.setComm(Integer.parseInt(words[6]));
            } catch (Exception e2) {
                e.setComm(0);
            }		
            e.setDeptno(Integer.parseInt(words[7]));

            //将这个员工输出
            context.write(new LongWritable(e.getDeptno()),e);
        }
    }
    ```

* Reducer：
    ```java
    import java.io.IOException;

    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.mapreduce.Reducer;

    public class EmployeeReducer extends Reducer<LongWritable, Employee, LongWritable, Employee> {

        @Override
        protected void reduce(LongWritable deptno, Iterable<Employee> values,Context context)
                throws IOException, InterruptedException {
            for(Employee e:values){
                context.write(deptno, e);
            }
        }

    }
    ```

* Employee：
    ```java
    import java.io.DataInput;
    import java.io.DataOutput;
    import java.io.IOException;

    import org.apache.hadoop.io.Writable;
    import org.apache.hadoop.io.WritableComparable;

    //7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
    public class Employee implements Writable{

        private int empno;
        private String ename;
        private String job;
        private int mgr;
        private String hiredate;
        private int sal;
        private int comm;
        private int deptno;

        public Employee(){

        }

        @Override
        public String toString() {
            return "Employee [empno=" + empno + ", ename=" + ename + ", job=" + job
                    + ", mgr=" + mgr + ", hiredate=" + hiredate + ", sal=" + sal
                    + ", comm=" + comm + ", deptno=" + deptno + "]";
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            this.empno = in.readInt();
            this.ename = in.readUTF();
            this.job = in.readUTF();
            this.mgr = in.readInt();
            this.hiredate = in.readUTF();
            this.sal = in.readInt();
            this.comm = in.readInt();
            this.deptno = in.readInt();
        }

        @Override
        public void write(DataOutput output) throws IOException {
            ////7499,ALLEN,SALESMAN,7698,1981/2/20,1600,300,30
            output.writeInt(empno);
            output.writeUTF(ename);
            output.writeUTF(job);
            output.writeInt(mgr);
            output.writeUTF(hiredate);
            output.writeInt(sal);
            output.writeInt(comm);
            output.writeInt(deptno);
        }

        public int getEmpno() {
            return empno;
        }

        public void setEmpno(int empno) {
            this.empno = empno;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getMgr() {
            return mgr;
        }

        public void setMgr(int mgr) {
            this.mgr = mgr;
        }

        public String getHiredate() {
            return hiredate;
        }

        public void setHiredate(String hiredate) {
            this.hiredate = hiredate;
        }

        public int getSal() {
            return sal;
        }

        public void setSal(int sal) {
            this.sal = sal;
        }

        public int getComm() {
            return comm;
        }

        public void setComm(int comm) {
            this.comm = comm;
        }

        public int getDeptno() {
            return deptno;
        }

        public void setDeptno(int deptno) {
            this.deptno = deptno;
        }
    }
    ```

* Partitioner：
    ```java
    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.mapreduce.Partitioner;

    public class EmployeePartition extends Partitioner<LongWritable, Employee> {

        @Override
        public int getPartition(LongWritable key2, Employee e, int numPartition) {
            // 分区的规则
            if(e.getDeptno() == 10){
                return 1%numPartition;
            }else if(e.getDeptno() == 20){
                return 2%numPartition;
            }else{
                return 3%numPartition;
            }
        }
    }
    ```

* Driver：
    ```java
    import org.apache.hadoop.conf.Configuration;
    import org.apache.hadoop.fs.Path;
    import org.apache.hadoop.io.LongWritable;
    import org.apache.hadoop.io.NullWritable;
    import org.apache.hadoop.mapreduce.Job;
    import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
    import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
    public class PartitionMain {

        public static void main(String[] args) throws Exception {
            // 求员工工资的总额
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            //指明程序的入口
            job.setJarByClass(PartitionMain.class);

            //指明任务中的mapper
            job.setMapperClass(EmployeeMapper.class);
            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(Employee.class);

            //设置分区的规则
            job.setPartitionerClass(EmployeePartition.class);
            job.setNumReduceTasks(3);

            job.setReducerClass(EmployeeReducer.class);
            job.setOutputKeyClass(LongWritable.class);
            job.setOutputValueClass(Employee.class);

            //指明任务的输入路径和输出路径	---> HDFS的路径
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //启动任务
            job.waitForCompletion(true);
        }
    }
    ```

### （五）Shuffle的过程

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/07-MapReduce基础/imgs/mr-shuffle.png)





