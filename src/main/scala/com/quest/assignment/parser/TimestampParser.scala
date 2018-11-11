package com.quest.assignment.parser

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.util.Try

class TimestampParser extends ParserSupport[LocalDateTime] {
  private val pattern = "yyyy-MM-dd HH:mm:ss.SSS"
  private val formatter = DateTimeFormatter.ofPattern(pattern)

  override def parse(timestamp: String): Try[LocalDateTime] =
    Try(LocalDateTime.parse(timestamp, formatter))
}
