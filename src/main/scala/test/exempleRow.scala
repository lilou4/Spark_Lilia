import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType}


val schema = StructType(Seq(
	StructField("name", StringType),
	StructField("age", IntegerType)
))

val rdd = spark.sparkContext.parallelize(Seq(Row("Alice", 22), Row("Bob", 30)))
val df = spark.createDataFrame(rdd, schema)
