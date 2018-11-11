package com.quest.assignment.service

import com.quest.assignment.io.{FileReader, FileReaderSpec}
import com.quest.assignment.models._
import com.quest.assignment.parser._
import org.scalatest.{Matchers, WordSpec}

class FileGrowthRateServiceSpec extends WordSpec with Matchers {

  private val fileReader = new FileReader()
  private val fileMappingsDataSetPath = classOf[FileReaderSpec].getClassLoader().getResource("Files.csv").getPath()
  private val fileStatsDataSetPath = classOf[FileReaderSpec].getClassLoader().getResource("FileStats.csv").getPath()

  private val fileIdParser = new FileIdParser()
  private val fileMappingsParser = new FileMappingsParser(fileIdParser)

  private val timestampParser = new TimestampParser()
  private val fileSizeParser = new FileSizeParser()

  private val fileStatsParser = new FileStatsParser(fileIdParser, timestampParser, fileSizeParser)
  private val growthRateInBytesPerHour = new GrowthRateCalculator()

  private val service = new FileGrowthRateService(fileReader,
    fileMappingsParser,
    fileStatsParser,
    growthRateInBytesPerHour)

  "FileGrowthRateService" should {
    "calculate growth rates for the data set" in {
      val fileGrowthRatesByFileId = service.calculateFileGrowsRates(fileMappingsDataSetPath, fileStatsDataSetPath)
      fileGrowthRatesByFileId.size shouldEqual 2
      fileGrowthRatesByFileId(FileId(1)) should contain allElementsOf
        List(
          FileGrowthRate(FileId(1),
            FilePath("\"c:\\program files\\sql server\\master.mdf\""),
            timestampParser.parse("2015-03-25 23:55:45.787").get,
            FileSize(4276852),
            GrowthRate(34291.5)
          ),

          FileGrowthRate(FileId(1),
            FilePath("\"c:\\program files\\sql server\\master.mdf\""),
            timestampParser.parse("2015-03-26 00:56:49.909").get,
            FileSize(4308267),
            GrowthRate(30865.2)),

          FileGrowthRate(FileId(1),
            FilePath("\"c:\\program files\\sql server\\master.mdf\""),
            timestampParser.parse("2015-03-26 01:59:24.107").get,
            FileSize(4366566),
            GrowthRate(55904.5))
        )
      fileGrowthRatesByFileId(FileId(6)) shouldEqual List.empty
    }
  }
}
