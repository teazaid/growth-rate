package com.quest.assignment.parser

import com.quest.assignment.models._

import scala.util.Try

class FileStatsParser(
  fileIdParser: FileIdParser,
  timestampParser: TimestampParser,
  fileSizeParser: FileSizeParser) extends MultiFieldParserSupport[FileStats] {

  private[parser] def extractTimestamp(pattern: String): Try[String] =
    if (pattern.isEmpty)
      Try(sys.error("Empty string"))
    else
      Try(pattern.drop(1).dropRight(1)) // drop first and last characters

  override def delimiter: String = ","

  override def parse(rawData: String): Try[FileStats] = {
    val data = rawData.split(delimiter)

    for {
      fileId <- fileIdParser.parse(data(0))
      pattern <- extractTimestamp(data(1))
      timestamp <- timestampParser.parse(pattern)
      fileSize <- fileSizeParser.parse(data(2))
    } yield FileStats(fileId, timestamp, fileSize)
  }
}
