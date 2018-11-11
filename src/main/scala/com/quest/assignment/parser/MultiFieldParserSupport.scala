package com.quest.assignment.parser

trait MultiFieldParserSupport[T] extends ParserSupport[T] {
  def delimiter: String
}
