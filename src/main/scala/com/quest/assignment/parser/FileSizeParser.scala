package com.quest.assignment.parser

import com.quest.assignment.models.FileSize

import scala.util.Try

class FileSizeParser extends ParserSupport[FileSize] {
  override def parse(rawData: String): Try[FileSize] =
    Try(rawData.toLong).map(FileSize)
}
