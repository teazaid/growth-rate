package com.quest.assignment.io

import java.io.BufferedWriter

class CSVWriter[T](serializer: CSVSerializer[T]) {
  def writeToCsv(content: List[T])(writer: BufferedWriter): Unit = {
    writeHeader()(writer)
    for {
      line <- content
    } writeLine(line)(writer)
  }

  private[io] def writeHeader()(writer: BufferedWriter): Unit = {
    writer.write(serializer.header)
    writer.newLine()
  }

  private[io] def writeLine(content: T)(writer: BufferedWriter): Unit = {
    writer.write(serializer.serialize(content))
    writer.newLine()
  }
}
