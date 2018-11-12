package com.quest.assignment.io

import java.io.{BufferedWriter, CharArrayWriter}

import com.quest.assignment.models._
import com.quest.assignment.parser.TimestampParser
import org.scalatest.{Matchers, WordSpec}
import sun.security.action.GetPropertyAction

class CSVWriterSpec extends WordSpec with Matchers {
  private val serializer = new FileGrowthRateCSVSerializer()
  private val csvWriter = new CSVWriter(serializer)

  private val fileGrowthRate = FileGrowthRate(
    FileId(1),
    FilePath("c:\\program files\\sql server\\master.mdf"),
    new TimestampParser().parse("2015-03-25 23:55:45.787").get,
    FileSize(4276852),
    GrowthRate(34291.5))

  // line separator is OS specific
  private val lineSeparator = java.security.AccessController.doPrivileged(
    new GetPropertyAction("line.separator"))

  "CSVWriter" should {
    "write header" in {
      val charArrayWriter = new CharArrayWriter
      val bufferedWriter = new BufferedWriter(charArrayWriter)

      csvWriter.writeHeader()(bufferedWriter)
      charArrayWriter.close()
      bufferedWriter.close()
      charArrayWriter.toString shouldEqual s""""FileID","Name","Timestamp","SizeInBytes","GrowthRateInBytesPerHour"${lineSeparator}"""
    }

    "write csv content" in {
      val charArrayWriter = new CharArrayWriter
      val bufferedWriter = new BufferedWriter(charArrayWriter)

      csvWriter.writeLine(fileGrowthRate)(bufferedWriter)
      charArrayWriter.close()
      bufferedWriter.close()

      charArrayWriter.toString shouldEqual s"""1,c:\\program files\\sql server\\master.mdf,"2015-03-25 23:55:45.787",4276852,34291.5${lineSeparator}"""
    }

    "write header and content to csv" in {
      val charArrayWriter = new CharArrayWriter
      val bufferedWriter = new BufferedWriter(charArrayWriter)

      csvWriter.writeToCsv(List(fileGrowthRate))(bufferedWriter)

      charArrayWriter.close()
      bufferedWriter.close()

      charArrayWriter.toString shouldEqual
        s""""FileID","Name","Timestamp","SizeInBytes","GrowthRateInBytesPerHour"${lineSeparator}1,c:\\program files\\sql server\\master.mdf,"2015-03-25 23:55:45.787",4276852,34291.5${lineSeparator}"""
    }
  }
}
