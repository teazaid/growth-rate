package com.quest.assignment.io

import com.quest.assignment.parser.MultiFieldParserSupport

import scala.io.Source
import scala.util.{Failure, Success, Try}

class FileReader {
  def read[T](filePath: String, parser: MultiFieldParserSupport[T]): List[T] = Try {
    val source = Source.fromFile(filePath)

    val lines = source.getLines()

    val result = lines.foldLeft(List.empty[T]) { (parsedLines, currentLine) =>
      parser.parse(currentLine) match {
        case Success(parsedLine) => parsedLine :: parsedLines // scala list appends at the beginning of a list in constant time
        case Failure(_)          => parsedLines
      }
    }

    source.close()

    result.reverse
  }.getOrElse(List.empty[T])
}
