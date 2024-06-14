package hadoop.java.unique_visitor;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UniqueVisitorMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text ipUrlRef = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Assuming the IP address is the first part, URL is the seventh part and referer is the eleventh part of the log line
        String line = value.toString();
        String[] parts = line.split("\\s+");
        if (parts.length > 10) {
            String ip = parts[0];
            String url = parts[6];
            String referer = parts[10];
            ipUrlRef.set(ip + " " + url + " " + referer);
            context.write(ipUrlRef, one);
        }
    }
}

