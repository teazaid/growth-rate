package com.quest.assignment.io

trait CSVSerializer[T] extends Serializer[T] with WithColumnHeaders {
  def delimiter: String = ","
  def header: String = columnHeaders.mkString(delimiter)
}
