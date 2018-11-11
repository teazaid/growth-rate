package com.quest.assignment.parser

import scala.util.Try

trait ParserSupport[T] {
  def parse(rawData: String): Try[T]
}
