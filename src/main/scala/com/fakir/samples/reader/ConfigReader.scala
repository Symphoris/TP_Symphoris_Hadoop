package com.fakir.samples.reader

import scala.io.Source

object ConfigReader {
  def readConfig(configPath: String): String = {
    Source.fromFile(configPath).getLines().mkString
  }
}
