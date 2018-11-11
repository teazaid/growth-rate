package com.quest.assignment.parser

import com.quest.assignment.models.{FileId, FileSize}
import org.scalatest.{Matchers, WordSpec}

class FileStatsParserSpec extends WordSpec with Matchers {
  private val timestamp = "2015-03-25 23:00:16.902"

  private val timestampParser = new TimestampParser()
  private val fileIdParser = new FileIdParser()
  private val fileSizeParser = new FileSizeParser()

  private val fileStatsMultiFieldParser = new FileStatsParser(
    fileIdParser,
    timestampParser,
    fileSizeParser)

  "FileStatsParser" should {

    "extract timestamp" in {
      fileStatsMultiFieldParser.extractTimestamp("\"2015-03-25 23:00:16.902\"").get shouldEqual timestamp
    }

    "not extract timestamp from empty string" in {
      fileStatsMultiFieldParser.extractTimestamp("").isFailure shouldEqual true
    }

    "parse raw data to file stats" in {
      val rawData = "1,\"2015-03-25 23:00:16.902\",4245143"
      val fileStatsT = fileStatsMultiFieldParser.parse(rawData)

      fileStatsT.get.fileId shouldEqual FileId(1)
      fileStatsT.get.localDateTime shouldEqual timestampParser.parse(timestamp).get
      fileStatsT.get.fileSize shouldEqual FileSize(4245143)
    }

    "not parse file stats with corrupted file Id" in {
      fileStatsMultiFieldParser.parse("Y,\"2015-03-25 23:00:16.902\",4245143").isFailure shouldEqual true
    }

    "not parse file stats with corrupted file size" in {
      fileStatsMultiFieldParser.parse("1,\"2015-03-25 23:00:16.902\",aSDS").isFailure shouldEqual true
    }
  }
}
