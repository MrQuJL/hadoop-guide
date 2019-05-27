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


















