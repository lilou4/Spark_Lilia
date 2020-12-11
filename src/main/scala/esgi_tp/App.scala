import org.apache.spark.sql.{SparkSession, functions}
import org.apache.spark.sql.Row
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType, DoubleType, FloatType}
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.functions._


object App {
      
      def main(args: Array[String]):Unit={
        println("Hello, world!")

         val spark = SparkSession.builder().appName("job-1").master("local[*]").getOrCreate()

         val communes_df = spark.read.option("header",true).option("delimiter", ";")
            .csv("/opt/scala-maven/src/main/scala/esgi_tp/Communes.csv")
            .select("DEPCOM", "PTOT")
         
         communes_df.show(3)
         communes_df.printSchema()


         val cpinsee_df = spark.read.option("header",true)
            .option("delimiter", ";")
            .csv("/opt/scala-maven/src/main/scala/esgi_tp/code-insee-postaux-geoflar.csv")
            .select("CODE INSEE", "Code Dept", "geom_x_y")

         cpinsee.show(3)
         cpinsee.printSchema()


         val poste_synop_df = spark.read.option("header",true)
            .option("delimiter", ";")
            .csv("/opt/scala-maven/src/main/scala/esgi_tp/postesSynop.txt")
            .select("ID", "Latitude", "Longitude")
         
         poste_synop_df.show(3)
         poste_synop_df.printSchema()



         val synoptemp_df = spark.read.option("header",true)
            .option("delimiter", ";")
            .csv("/opt/scala-maven/src/main/scala/esgi_tp/synop.2020120512.txt")
            .select("date", "numer_sta", "t")  
         synoptemp_df.show(3)
         synoptemp_df.printSchema()


         val Tem_C : Double => Double = _-273.15
         val extract_Tem_C = udf(Tem_C)

         val synoptemp2_df= synoptemp_df.withColumn("Tem_C",extract_Tem_C(col("t")))

         val cpinsee_df= cpinsee2_df.join(communes2_df,cpinsee2_df("CODE INSEE") === communes2_df("DEPCOM"), "left_outer")
         println(cpinsee_df.show(5))

      }
}