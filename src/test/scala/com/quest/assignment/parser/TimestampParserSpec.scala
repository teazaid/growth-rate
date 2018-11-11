package com.quest.assignment.parser

import org.scalatest.{Matchers, WordSpec}

class TimestampParserSpec extends WordSpec with Matchers {
  private val timestamp = "2015-03-25 23:00:16.902"
  private val timestampParser = new TimestampParser()

  "TimestampParser" should {
    "parse timestamp to Date" in {
      val localDateTimeT = timestampParser.parse(timestamp)
      val localDateTime = localDateTimeT.get
      localDateTime.getYear shouldEqual 2015
      localDateTime.getMonth.getValue shouldEqual 3
      localDateTime.getDayOfMonth shouldEqual 25
      localDateTime.getHour shouldEqual 23
      localDateTime.getMinute shouldEqual 0
      localDateTime.getSecond shouldEqual 16
      localDateTime.getNano shouldEqual 902000000
    }

    "not parse corrupted timestamp" in {
      val localDateTimeT = timestampParser.parse("2015-0F-25 23:00:16.902")
      localDateTimeT.isFailure shouldEqual true
    }
  }
}
