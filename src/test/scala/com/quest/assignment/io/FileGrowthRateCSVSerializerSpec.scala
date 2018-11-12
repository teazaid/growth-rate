package com.quest.assignment.io

import com.quest.assignment.models._
import com.quest.assignment.parser.TimestampParser
import org.scalatest.{Matchers, WordSpec}

class FileGrowthRateCSVSerializerSpec extends WordSpec with Matchers {
  "FileGrowthRateCSVSerializer" should {
    "serialize FileGrowth to String" in {
      val fileGrowthRateSerializer = new FileGrowthRateCSVSerializer()

      fileGrowthRateSerializer.serialize(
        FileGrowthRate(
          FileId(1),
          FilePath("c:\\program files\\sql server\\master.mdf"),
          new TimestampParser().parse("2015-03-25 23:55:45.787").get,
          FileSize(4276852),
          GrowthRate(34291.5)
        )
      ) shouldEqual "1,c:\\program files\\sql server\\master.mdf,\"2015-03-25 23:55:45.787\",4276852,34291.5"
    }
  }
}
