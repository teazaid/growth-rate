package com.quest.assignment.service

import com.quest.assignment.io.FileReader
import com.quest.assignment.models.{FileGrowthRate, FileId, FileMapping, FileStats}
import com.quest.assignment.parser.{FileMappingsParser, FileStatsParser}

import scala.annotation.tailrec

class FileGrowthRateService(
  fileReader: FileReader,
  fileMappingsParser: FileMappingsParser,
  fileStatsParser: FileStatsParser,
  growthRateInBytesPerHour: GrowthRateCalculator) {

  def calculateFileGrowsRates(fileMappingsDataSetPath: String, fileStatsDataSetPath: String): Map[FileId, List[FileGrowthRate]] = {
    val fileMappings = fileReader.read(fileMappingsDataSetPath, fileMappingsParser)
      .groupBy(_.fileId)
      .map { case (fileId, fileMappings) => fileId -> fileMappings.head }

    val fileStatsByFileId = fileReader.read(fileStatsDataSetPath, fileStatsParser).groupBy(_.fileId)

    fileStatsByFileId.map { case (fileId, fileStats) =>
      fileId -> fileGrowthRateFromFileStats(None, fileStats, fileMappings, List.empty).reverse
    }
  }

  @tailrec
  private def fileGrowthRateFromFileStats(previousOpt: Option[FileStats],
    fileStats: List[FileStats],
    fileMappings: Map[FileId, FileMapping],
    result: List[FileGrowthRate]): List[FileGrowthRate] = {

    previousOpt match {
      case Some(previous) =>
        fileStats match {
          case current :: rest =>
            val fileGrowthRate = currentFileGrowthRate(previous, current, fileMappings)
            fileGrowthRateFromFileStats(Some(current), rest, fileMappings, fileGrowthRate :: result)
          case Nil             =>
            result
        }
      case None           =>
        fileStats match {
          case current :: next :: rest =>
            fileGrowthRateFromFileStats(Some(current), next :: rest, fileMappings, result)
          case _                       =>
            result
        }
    }
  }

  private def currentFileGrowthRate(previous: FileStats,
    current: FileStats,
    fileMappings: Map[FileId, FileMapping]): FileGrowthRate = {

    val ratePerHour = growthRateInBytesPerHour.ratePerHour(previous, current)

    FileGrowthRate(
      previous.fileId,
      fileMappings(previous.fileId).filePath,
      current.localDateTime,
      current.fileSize,
      ratePerHour
    )
  }
}
