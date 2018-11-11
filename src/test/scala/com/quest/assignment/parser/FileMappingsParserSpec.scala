package com.quest.assignment.parser

import com.quest.assignment.models.{FileId, FilePath}
import org.scalatest.{Matchers, WordSpec}

class FileMappingsParserSpec extends WordSpec with Matchers {
  private val parser = new FileMappingsParser(new FileIdParser())

  "FilesMappingParser" should {
    "parse raw string with mappings" in {
      val rawData = "1,\"c:\\program files\\sql server\\master.mdf\""
      val fileMapping = parser.parse(rawData)
      fileMapping.isSuccess shouldBe true
      fileMapping.get.fileId shouldEqual FileId(1)
      fileMapping.get.filePath shouldEqual FilePath("\"c:\\program files\\sql server\\master.mdf\"")
    }

    "not parse file mappings with corrupted file id" in {
      val rawData = "id,\"c:\\program files\\sql server\\master.mdf\""
      val fileMapping = parser.parse(rawData)
      fileMapping.isFailure shouldBe true
    }
  }

}
