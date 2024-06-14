package hadoop.java.device;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DeviceMapper extends Mapper<Object, Text, Text, IntWritable>{
    private final static IntWritable one = new IntWritable(1);
    private Text deviceType = new Text();
    
    private final static Pattern userAgentPattern = Pattern.compile(
        ".*\".*\".*\"(.*)\".*");

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        Matcher matcher = userAgentPattern.matcher(line);
        if (matcher.matches()) {
            String userAgent = matcher.group(1);
            String device = identifyDevice(userAgent);
            deviceType.set(device);
            context.write(deviceType, one);
        }
    }

    private String identifyDevice(String userAgent) {
        if (userAgent.toLowerCase().contains("mobile")) {
            return "Mobile";
        } else if (userAgent.toLowerCase().contains("tablet")) {
            return "Tablet";
        } else {
            return "Desktop";
        }
    }
}