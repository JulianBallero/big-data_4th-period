from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("WordCount").setMaster("local")
sc = SparkContext.getOrCreate(conf)

rdd = sc.textFile("data/bible.txt")

rdd.take(5)