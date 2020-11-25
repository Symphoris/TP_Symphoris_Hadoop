package com.fakir.samples.services

import java.io.File

import com.fakir.samples.config.ConfigParser
import com.fakir.samples.reader.ConfigReader
import javax.activation.{DataHandler, FileDataSource}
import org.apache.spark.sql.{DataFrame, SparkSession}
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.mail.{Authenticator, Message, PasswordAuthentication, Session, Transport}

import scala.collection.mutable.ListBuffer

object Service3 {

  def sendFileClient(df: DataFrame, clientId: String, clientEmail: String): DataFrame = {

    import org.apache.spark.sql.functions._
    import ss.sqlContext.implicits._
    import org.apache.spark.sql.functions._
    val ss = SparkSession.builder().appName("test").getOrCreate()

    //client authentification
    val properties = System.getProperties
    properties.put("mail.smtp.host", "smtp.gmail.com")
    properties.put("mail.smtp.user", clientEmail)
    properties.put("mail.smtp.password", "clientPassword")
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", "587")
    properties.put("mail.smtp.starttls.enable", "true")
    val auth:Authenticator = new Authenticator() {
      override def getPasswordAuthentication = new
          PasswordAuthentication("tnsymphoris@gmail.com", "clientPassword")
    }
    print(df.count())
    df.filter(col("clientId") === clientId)

    val session = Session.getInstance(properties,auth)
    val message = new MimeMessage(session)
    val multipart = new MimeMultipart();
    val fileName = "donnees.csv"
    val messageBodyPart = new MimeBodyPart()
    messageBodyPart.setFileName(fileName)
    val pathToAttachment = "C:\\Users\\etudiant\\Pictures\\SPARK"
    val source: FileDataSource = new FileDataSource(pathToAttachment)
    messageBodyPart.setDataHandler(new DataHandler(source))
    multipart.addBodyPart(messageBodyPart)
    message.setFrom(new InternetAddress("tnsymphoris@gmail.com"))
    message.setRecipients(Message.RecipientType.TO, "tnsymphoris@gmail.com")
    message.setHeader("Content-Type", "text/html")
    message.setSubject(" ask Client data")
    message.setContent(multipart)
    Transport.send(message)






  }






}
