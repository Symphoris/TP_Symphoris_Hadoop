package com.fakir.samples.config
import scopt.{DefaultOParserSetup, OParserBuilder, OParserSetup}


case class ConfigParser(configPath: String = "", verbose: Boolean = false, inputPath: String = "", inputFormat: String = "",
                        outputFormat: String = "" , outputPath: String = "", service: String = "" , clientId: String = "",
                        clientEmail )

object ConfigParser {
  private val setup: OParserSetup = new DefaultOParserSetup {
    override def showUsageOnError: Option[Boolean] = Some(true)
    override def errorOnUnknownArgument = false
  }
  import scopt.OParser
  val builder: OParserBuilder[ConfigParser] = OParser.builder[ConfigParser]

  val parser: OParser[Unit, ConfigParser] = {
    import builder._

    OParser.sequence(
      programName("GDPR Compliance"),

      opt[String]("configPath")
        .required()
        .action((x, c) => c.copy(configPath = x))
        .text("Path of my configuration file"),

      opt[String]("inputPath")
        .required()
        .action((x, c) => c.copy(inputPath = x))
        .text("Path of input file"),

      opt[String]("inputFormat")
        .required()
        .action((x, c) => c.copy(inputFormat = x))
        .text("Format of input file"),
      opt[String]("outputPath")
        .required()
        .action((x, c) => c.copy(outputPath = x))
        .text("Path of output file"),
      opt[String]("outputFormat")
        .required()
        .action((x, c) => c.copy(outputFormat = x))
        .text("Format of output file"),
      opt[String]("service")
        .required()
        .action((x, c) => c.copy(service = x))
        .text("service to use on input file"),
      opt[String]("clientEmail")
        .required()
        .action((x, c) => c.copy(clientId = x))
        .text("clientEmail to sent message"),
      opt[String]("clientEmail")
        .required()
        .action((x, c) => c.copy(clientId = x))
        .text("clientId to delete from input file"),
      opt[Boolean]("verbose")
        .action((x, c) => c.copy(verbose = x))
        .text("Path of my configuration file")

    )
  }

  def parser(arguments: Array[String]): Option[ConfigParser] = {
    OParser.parse(ConfigParser.parser, arguments, ConfigParser(), setup)
  }

  def getConfigArgs(args: Array[String]): ConfigParser = {
    ConfigParser.parser(args) match {
      case Some(config) => config
      case _ => {
        print("cannot parse conf")
        sys.exit(1)
      }
    }
  }


}
