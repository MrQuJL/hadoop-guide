import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class TestMain {

	public static void main(String[] args) throws Exception {
		// 使用HDFS的API创建目录
		//设置NameNode地址
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.88.11:9000");
		
		//得到HDFS的文件系统
		FileSystem fs = FileSystem.get(conf);
        fs.mkdirs(new Path("/folder1"));
	}

}
