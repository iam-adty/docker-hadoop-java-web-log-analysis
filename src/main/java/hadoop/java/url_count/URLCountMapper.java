package hadoop.java.url_count;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class URLCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text url = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Assuming the URL is in a specific part of the log line, e.g., after the HTTP method
        String line = value.toString();
        String[] parts = line.split("\\s+");
        if (parts.length > 6) {
            // This is a simple assumption; adjust the index based on your log format
            String accessedURL = parts[6];
            url.set(accessedURL);
            context.write(url, one);
        }
    }
}
