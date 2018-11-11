package com.quest.assignment.parser

import com.quest.assignment.models.{FileMapping, FilePath}

import scala.util.Try

class FileMappingsParser(fileIdParser: FileIdParser) extends MultiFieldParserSupport[FileMapping] {

  override def delimiter: String = ","

  override def parse(rawData: String): Try[FileMapping] = {
    val data = rawData.split(delimiter)

    fileIdParser.parse(data(0)).map { fileId =>
      FileMapping(fileId, FilePath(data(1)))
    }
  }
}


