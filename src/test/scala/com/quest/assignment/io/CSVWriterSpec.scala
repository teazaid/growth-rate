package com.quest.assignment.io

import java.io.{BufferedWriter, CharArrayWriter}

import com.quest.assignment.models._
import com.quest.assignment.parser.TimestampParser
import org.scalatest.{Matchers, WordSpec}

class CSVWriterSpec extends WordSpec with Matchers {
  private val serializer = new FileGrowthRateSerializer()
  private val csvWriter = new CSVWriter(serializer)

  private val fileGrowthRate = FileGrowthRate(
    FileId(1),
    FilePath("c:\\program files\\sql server\\master.mdf"),
    new TimestampParser().parse("2015-03-25 23:55:45.787").get,
    FileSize(4276852),
    GrowthRate(34291.5))

  private val headerColumns = List(
    "\"FileID\"",
    "\"Name\"",
    "\"Timestamp\"",
    "\"SizeInBytes\"",
    "\"GrowthRateInBytesPerHour\""
  )

  "CSVWriter" should {
    "write header" in {
      val charArrayWriter = new CharArrayWriter
      val bufferedWriter = new BufferedWriter(charArrayWriter)

      csvWriter.writeHeader(headerColumns)(bufferedWriter)
      charArrayWriter.close()
      bufferedWriter.close()
      charArrayWriter.toString shouldEqual "\"FileID\",\"Name\",\"Timestamp\",\"SizeInBytes\",\"GrowthRateInBytesPerHour\"\n"
    }

    "write csv content" in {
      val charArrayWriter = new CharArrayWriter
      val bufferedWriter = new BufferedWriter(charArrayWriter)

      csvWriter.writeLine(fileGrowthRate)(bufferedWriter)
      charArrayWriter.close()
      bufferedWriter.close()

      charArrayWriter.toString shouldEqual "1,c:\\program files\\sql server\\master.mdf,\"2015-03-25 23:55:45.787\",4276852,34291.5\n"
    }

    "write header and content to csv" in {
      val charArrayWriter = new CharArrayWriter
      val bufferedWriter = new BufferedWriter(charArrayWriter)

      csvWriter.writeToCsv(headerColumns, List(fileGrowthRate))(bufferedWriter)

      charArrayWriter.close()
      bufferedWriter.close()

      charArrayWriter.toString shouldEqual
        "\"FileID\",\"Name\",\"Timestamp\",\"SizeInBytes\",\"GrowthRateInBytesPerHour\"\n" +
        "1,c:\\program files\\sql server\\master.mdf,\"2015-03-25 23:55:45.787\",4276852,34291.5\n"
    }
  }
}
