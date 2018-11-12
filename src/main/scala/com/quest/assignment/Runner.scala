package com.quest.assignment

import java.io.{BufferedWriter, FileWriter}

import com.quest.assignment.io.{CSVWriter, FileGrowthRateCSVSerializer, FileReader}
import com.quest.assignment.models.{FileGrowthRate, FileId}
import com.quest.assignment.parser._
import com.quest.assignment.service.{FileGrowthRateService, GrowthRateCalculator}

object Runner {
  def main(args: Array[String]): Unit = {
    val fileReader = new FileReader()
    val fileMappingsDataSetPath = getClass.getResource("/Files.csv").getPath
    val fileStatsDataSetPath = getClass.getResource("/FileStats.csv").getPath

    val fileIdParser = new FileIdParser()
    val fileMappingsParser = new FileMappingsParser(fileIdParser)

    val timestampParser = new TimestampParser()
    val fileSizeParser = new FileSizeParser()

    val fileStatsParser = new FileStatsParser(fileIdParser, timestampParser, fileSizeParser)
    val growthRateInBytesPerHour = new GrowthRateCalculator()

    val csvWriter = new CSVWriter(new FileGrowthRateCSVSerializer())

    val fileGrowthRateService = new FileGrowthRateService(fileReader,
      fileMappingsParser,
      fileStatsParser,
      growthRateInBytesPerHour)

    val fileGrowthRatesByFileId = fileGrowthRateService.calculateFileGrowsRates(fileMappingsDataSetPath, fileStatsDataSetPath)
    writeFileGrowthRatesByFileId(csvWriter, fileGrowthRatesByFileId)

    println("Done")
  }

  private def writeFileGrowthRatesByFileId(csvWriter: CSVWriter[FileGrowthRate],
    fileGrowthRatesByFileId: Map[FileId, List[FileGrowthRate]]): Unit =

    fileGrowthRatesByFileId.foreach { case (fileId, fileGrowthRates) =>
      val bufferedWriter = new BufferedWriter(new FileWriter(s"${ fileId.value }.csv"))

      csvWriter.writeToCsv(fileGrowthRates)(bufferedWriter)

      bufferedWriter.close()
    }

}
