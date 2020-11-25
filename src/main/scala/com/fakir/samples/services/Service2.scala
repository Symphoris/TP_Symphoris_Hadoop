package com.fakir.samples.services

import java.security.MessageDigest
import org.apache.spark.sql.{SparkSession, SQLContext}


import org.apache.spark.sql.DataFrame

object Service2 {

  def hashIdClient(df: DataFrame, clientIdToHash: String): DataFrame = {
    import org.apache.spark.sql.functions._

    import org.apache.spark.sql.functions._

    val ss = SparkSession.builder().appName("test").getOrCreate()
    import ss.sqlContext.implicits._
    print(df.count())
    df.withColumn("clientId", when(col("clientId")=== clientIdToHash, hash($"clientId"))
      .otherwise(col("clientId")))
    

  }

}
