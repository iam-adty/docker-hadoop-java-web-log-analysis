package hadoop.java.ip_count;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class IPCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text ip = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Assuming the IP address is the first part of the log line, separated by spaces
        String line = value.toString();
        String[] parts = line.split("\\s+");
        if (parts.length > 0) {
            ip.set(parts[0]);
            context.write(ip, one);
        }
    }
}

