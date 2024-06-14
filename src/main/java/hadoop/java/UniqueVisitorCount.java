package hadoop.java;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import hadoop.java.unique_visitor.UniqueVisitorCombiner;
import hadoop.java.unique_visitor.UniqueVisitorMapper;
import hadoop.java.unique_visitor.UniqueVisitorReducer;

public class UniqueVisitorCount {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: UniqueVisitorCount <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Unique Visitor Count");

        job.setJarByClass(UniqueVisitorCount.class);
        job.setMapperClass(UniqueVisitorMapper.class);
        job.setCombinerClass(UniqueVisitorCombiner.class); // Optional combiner to optimize
        job.setReducerClass(UniqueVisitorReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
