package com.quest.assignment.parser

import com.quest.assignment.models.FileId
import org.scalatest.{Matchers, WordSpec}

import scala.util.Success

class FileIdParserSpec extends WordSpec with Matchers {

  private val parser = new FileIdParser()

  "FileIdParser" should {
    "parse fileId" in {
      parser.parse("1") shouldEqual Success(FileId(1))
    }

    "not parse empty string" in {
      parser.parse("").isFailure shouldBe true
    }
  }
}
