package com.fakir.samples.services

import org.apache.spark.sql.DataFrame

object Service1 {

  def filterClientId(df: DataFrame, clientIdToDelete: String): DataFrame = {
  import org.apache.spark.sql.functions._
    print(df.count())
     df.filter(col("clientId") =!= clientIdToDelete)

  }


}
