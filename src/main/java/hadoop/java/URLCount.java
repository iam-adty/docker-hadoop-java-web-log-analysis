package hadoop.java;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import hadoop.java.url_count.URLCountMapper;
import hadoop.java.url_count.URLCountReducer;

public class URLCount {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: URLCount <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "URL Count");

        job.setJarByClass(URLCount.class);
        job.setMapperClass(URLCountMapper.class);
        job.setCombinerClass(URLCountReducer.class); // Optional combiner to optimize
        job.setReducerClass(URLCountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

