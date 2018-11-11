package com.quest.assignment.parser

import com.quest.assignment.models.FileId

import scala.util.Try

class FileIdParser extends ParserSupport[FileId] {
  override def parse(rawData: String): Try[FileId] =
    Try(rawData.toLong).map(FileId)
}
