package com.mytask
/**
  * @author Victor Pobedrya, pobedrik@gmail.com
  * @version 1.0
  */

import java.io.File

import scala.collection.mutable.ArrayBuffer

object Crimes extends  App {

  override def main(args: Array[String]): Unit = {
    try {
      if (args(0) != "-d"){
        throw new ArrayIndexOutOfBoundsException()
      }
      val directory_path = args(1).toString.trim
      val crime_records = ArrayBuffer[Map[String, String]]()
      val crime_files = getListOfFiles(directory_path)
      for (file_name <- crime_files) {
        crime_records.appendAll(load_records(file_name.getAbsolutePath))
      }
      val grouped_records = group_records(crime_records)
      print_records(grouped_records)
    } catch {
      case e: ArrayIndexOutOfBoundsException => println("Usage:  java -jar crime.jar -d <absolute_path_to_the_crimes_folder>")
    }
  }

  /**
    * @return Returns list of files in specified directory
    */
  def getListOfFiles(dir: String):List[File] = {

    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  /**
    * @return Load all records from csv files with non-empty 'Crime ID' in one ArrayBuffer
    */
  def load_records(file_name: String): ArrayBuffer[Map[String, String]] = {
    val valid_crimes = ArrayBuffer[Map[String, String]]()
    val bufferedSource = io.Source.fromFile(file_name)

    try {

      val file_iterator = bufferedSource.getLines
      val header_line = file_iterator.next()
      val header_cols = header_line.split(",").map(_.trim)

      for (line <- file_iterator) {
        val cols = line.split(",").map(_.trim)
        val record = header_cols.zip(cols).toMap
        if (record("Crime ID").nonEmpty) {
          valid_crimes += record
        }
      }

    } finally {
      bufferedSource.close
    }
    valid_crimes
  }


  /**
    * @return Group records in locations an returns top 5 most criminal locations
    */
  def group_records(crime_records: ArrayBuffer[Map[String, String]]): ArrayBuffer[((String, String), ArrayBuffer[Map[String, String]])] = {
    var grouped_records = crime_records.groupBy(record => (record("Latitude"), record("Longitude")))
    var most_criminal = ArrayBuffer[((String, String), ArrayBuffer[Map[String, String]])]()
    for (i <- 1 to 5) {
      val record = grouped_records.toSeq.maxBy(_._2.size)
      grouped_records -= record._1
      most_criminal += record
    }
    most_criminal
  }


  /**
    * @return Prints most criminal locations and crimes, each with list of associated theft incidents,
    */
  def print_records(records:  ArrayBuffer[((String, String), ArrayBuffer[Map[String, String]])]): Unit = {
    for (record <- records){
      println("-"*35)
      println(record._1+":"+record._2.size)
      for (theft <- record._2) {
        println(theft("Crime type"))
      }
    }
    println("-"*35)
  }
}
