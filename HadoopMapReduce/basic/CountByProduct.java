package basic;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class CountByProduct {

    public static class AverageOfReviewCountsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static Text dummyKey = new Text("media");

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parts = value.toString().split("\t");
            if (parts.length == 2) {
                try {
                    int count = Integer.parseInt(parts[1]);
                    context.write(dummyKey, new IntWritable(count));
                } catch (NumberFormatException e) {
                    // Log ou ignore
                }
            }
        }
    }

    public static class AverageOfReviewCountsReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0, totalProducts = 0;
            for (IntWritable val : values) {
                sum += val.get();
                totalProducts++;
            }
            if (totalProducts > 0) {
                context.write(new Text("Média de avaliações por produto:"), new DoubleWritable((double) sum / totalProducts));
            }
        }
    }
}



