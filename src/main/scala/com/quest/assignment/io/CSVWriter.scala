package com.quest.assignment.io

import java.io.BufferedWriter

class CSVWriter[T](serializer: Serializer[T], delimiter: String = ",") {
  def writeToCsv(headers: List[String], content: List[T])(writer: BufferedWriter): Unit = {
    writeHeader(headers)(writer)
    for {
      line <- content
    } writeLine(line)(writer)
  }

  private[io] def writeHeader(headerColumns: List[String])(writer: BufferedWriter): Unit = {
    writer.write(headerColumns.mkString(delimiter))
    writer.newLine()
  }

  private[io] def writeLine(content: T)(writer: BufferedWriter): Unit = {
    writer.write(serializer.serialize(content))
    writer.newLine()
  }
}
