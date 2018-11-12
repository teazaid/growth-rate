package com.quest.assignment.service

import com.quest.assignment.models.GrowthRate
import com.quest.assignment.parser.{FileIdParser, FileSizeParser, FileStatsParser, TimestampParser}
import org.scalatest.{Matchers, WordSpec}

class GrowthRateCalculatorSpec extends WordSpec with Matchers {
  private val timestampParser = new TimestampParser()
  private val fileIdParser = new FileIdParser()
  private val fileSizeParser = new FileSizeParser()

  private val fileStatsMultiFieldParser = new FileStatsParser(
    fileIdParser,
    timestampParser,
    fileSizeParser)

  private val calc = new GrowthRateCalculator()

  "GrowthRateCalculator" should {
    "return timestamps difference in milliseconds" in {
      val startDate = timestampParser.parse("2015-03-25 23:00:16.902").get
      val endDate = timestampParser.parse("2015-03-25 23:55:45.787").get

      calc.millisDiff(startDate, endDate) shouldEqual 3328885
    }

    "calculate growth rate in bytes between file stats" in {
      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 23:00:16.902\",4245143").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 23:55:45.787\",4276852").get
      ) shouldEqual GrowthRate(34291.5)

      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 23:00:00.000\",4245143").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 00:00:00.000\",4276852").get
      ) shouldEqual GrowthRate(4276852 - 4245143)

      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 23:00:00.000\",4245143").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 01:00:00.000\",4276852").get
      ) shouldEqual GrowthRate((4276852 - 4245143) / 2.0)

      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 23:55:45.787\",4276852").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 00:56:49.909\",4308267").get
      ) shouldEqual GrowthRate(30865.2)

      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 00:56:49.909\",4308267").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 01:59:24.107\",4366566").get
      ) shouldEqual GrowthRate(55904.5)

      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 00:56:49.909\",4308267").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-26 01:10:49.909\",4308267").get
      ) shouldEqual GrowthRate(0.0)

      calc.ratePerHour(
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 10:00:16.902\",1000000").get,
        fileStatsMultiFieldParser.parse("1,\"2015-03-25 12:00:16.902\",2000000").get
      ) shouldEqual GrowthRate(500000.0)
    }
  }
}
