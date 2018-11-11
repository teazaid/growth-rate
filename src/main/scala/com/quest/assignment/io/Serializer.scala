package com.quest.assignment.io

trait Serializer[T] {
  def serialize(data: T): String
}
