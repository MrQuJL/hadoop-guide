package demo;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable>{

	@Override
	protected void reduce(Text k3, Iterable<LongWritable> v3,Context context) throws IOException, InterruptedException {
		//v3: 是一个集合，每个元素就是v2
		long total = 0;
		for(LongWritable l:v3){
			total = total + l.get();
		}
		
		//输出
		context.write(k3, new LongWritable(total));
	}

}
