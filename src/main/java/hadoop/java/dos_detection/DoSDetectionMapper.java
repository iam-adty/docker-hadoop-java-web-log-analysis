package hadoop.java.dos_detection;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoSDetectionMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text ipTime = new Text();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split("\\s+");
        if (parts.length > 3) {
            try {
                String ip = parts[0];
                String timestamp = parts[3].substring(1); // remove leading '['
                Date date = dateFormat.parse(timestamp);
                
                // Round date to the nearest minute
                date.setSeconds(0);
                String roundedTime = dateFormat.format(date);

                ipTime.set(ip + " " + roundedTime);
                context.write(ipTime, one);
            } catch (ParseException e) {
                // Handle parse exception
                e.printStackTrace();
            }
        }
    }
}
