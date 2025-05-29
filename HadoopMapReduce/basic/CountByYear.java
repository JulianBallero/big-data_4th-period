//package basic;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.util.GenericOptionsParser;
//import org.apache.log4j.BasicConfigurator;
//
//import java.io.IOException;
//
//public class CountByYear {
//
//    public static void main(String[] args) throws Exception {
//        BasicConfigurator.configure();
//
//        Configuration c = new Configuration();
//        String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
//        // Caminhos de entrada e saída
//        Path input = new Path("in/Reviews.csv");
//        Path output = new Path("output/countReviewsByYear.txt");
//
//        // Configuração do Job
//        Job j = new Job(c, "countByYear");
//        j.setJarByClass(CountByYear.class);
//        j.setMapperClass(MapforYearCategoryCount.class);
//        j.setReducerClass(ReduceForYearCategoryCount.class);
//
//        // Configurar tipos de dados de saída
//        j.setMapOutputKeyClass(Text.class);
//        j.setMapOutputValueClass(IntWritable.class);
//        j.setOutputKeyClass(Text.class);
//        j.setOutputValueClass(IntWritable.class);
//
//        FileInputFormat.addInputPath(j, input);
//        FileOutputFormat.setOutputPath(j, output);
//
//        System.exit(j.waitForCompletion(true) ? 0 : 1);
//        }
//
//    public static class MapforYearCategoryCount extends Mapper<LongWritable, Text, Text, IntWritable> {
//
//        public void map(LongWritable key, Text value, Context con)
//                throws IOException, InterruptedException {
//            // Ignorar a primeira linha (cabeçalho)
//            String line = value.toString();
//            if (key.get() == 0 && line.contains("Age")) return;
//
//            // Separar as colunas por vírgula
//            String[] columns = line.split(",");
//
//            // Coluna "Age Category" é o índice 2
//            String ageCategory = columns[2].trim();
//            if (!ageCategory.isEmpty()) {
//                con.write(new Text(ageCategory), new IntWritable(1));
//            }
//        }
//    }
//
//    public static class ReduceForYearCategoryCount extends Reducer<Text, IntWritable, Text, IntWritable> {
//
//        public void reduce(Text key, Iterable<IntWritable> values, Context con)
//                throws IOException, InterruptedException {
//
//            int count = 0;
//            for (IntWritable valor : values) {
//                count += valor.get();
//            }
//
//            con.write(key, new IntWritable(count));
//        }
//    }
//}
