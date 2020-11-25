//import .elementLePlusProche
import com.fakir.samples.config.{ConfigParser, JsonConfig}
import com.fakir.samples.reader.{ConfigReader, SparkReaderWriter}
import com.fakir.samples.services.{Service1, Service2, Service3}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Column, DataFrame, Row, SparkSession}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{ArrayType, IntegerType, MapType, StringType, StructType}
import org.apache.spark.sql.functions._
import spray.json._


object SampleProgram {


  def elementLePlusProche(elem1: Int, elem2: Int, comparedNumber: Int): Int = {
    if(Math.abs(elem1 - comparedNumber) > Math.abs(elem2 - comparedNumber))
      elem2
    else
      elem1
  }

  def majuscule(s: String): String = {
    if(s.contains("Barca"))
      s.toUpperCase
    else
      s
  }

  def keepWhenContains(element: String, stringToFind: String): Boolean = {
    if(element.contains(stringToFind))
      true
    else
      false
  }
  def main(args: Array[String]): Unit=  {
    Logger.getLogger("org").setLevel(Level.OFF)
    val configCli: ConfigParser = ConfigParser.getConfigArgs(args)

    implicit val spark: SparkSession = SparkSession.builder().master("local").getOrCreate()

    val df = SparkReaderWriter.readData(configCli.inputPath, configCli.inputFormat)
    val df1 = SparkReaderWriter.readData(configCli.inputPath, configCli.inputFormat)
    configCli.service match {
      case "service1" =>  val  result: DataFrame = Service1.filterClientId(df, configCli.clientId)
         SparkReaderWriter.writeData(result, configCli.outputPath, configCli.outputFormat)
        println(result.count())


      case "service2" =>  val  result: DataFrame = Service2.hashIdClient(df1, configCli.clientId)
        SparkReaderWriter.writeData(result, configCli.outputPath, configCli.outputFormat)
        println(result.count())

       case "service3" =>  val  result: DataFrame = Service3.sendFileClient(df1, configCli.clientId, configCli.clientEmail)
    SparkReaderWriter.writeData(result, configCli.outputPath, configCli.outputFormat)
    println(result.count())

      case _ => {
        println("Service non reconnu")
        sys.exit(1)
      }
    }




  }
}