package demo;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		/*
		 * key: 输入的key
		 * value: 数据   I love Beijing
		 * context: Map上下文
		 */
		String data= value.toString();
		//分词
		String[] words = data.split(" ");
		
		//输出每个单词
		for(String w:words){
			context.write(new Text(w), new LongWritable(1));
		}
	}

}
















