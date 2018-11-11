package com.quest.assignment.io

import com.quest.assignment.models.{FileId, FileMapping, FilePath}
import com.quest.assignment.parser.{FileIdParser, FileMappingsParser, MultiFieldParserSupport}
import org.scalatest.{Matchers, WordSpec}

import scala.util.{Success, Try}

class FileReaderSpec extends WordSpec with Matchers {

  private val fileMappingsParser = new FileMappingsParser(new FileIdParser())
  private val path = classOf[FileReaderSpec].getClassLoader().getResource("Files.csv").getPath()
  private val fileReader = new FileReader()

  "FileReader" should {
    "should read a file with a plain parser" in {
      val identityParser = new MultiFieldParserSupport[String] {
        override def delimiter: String = ","
        override def parse(rawData: String): Try[String] = Success(rawData)
      }

      val fileMappings = fileReader.read(path, identityParser)
      fileMappings.size shouldEqual 5

      fileMappings should contain allElementsOf
        List(
          "\"ID\",\"Name\"",
          "1,\"c:\\program files\\sql server\\master.mdf\"",
          "ABC,\"c:\\program files\\sql server\\customer-part-2012.mdf\"",
          "4,\"c:\\program files\\sql server\\customer-part-2011.mdf\"",
          "6,\"c:\\program files\\sql server\\customer-part-2012.mdf\""
      )
    }

    "should read a file and skip lines that weren't parsed" in {
      val fileMappings = fileReader.read(path, fileMappingsParser)
      fileMappings.size shouldEqual 3

      fileMappings should contain allElementsOf
        List(
          FileMapping(FileId(1), FilePath("\"c:\\program files\\sql server\\master.mdf\"")),
          FileMapping(FileId(4), FilePath("\"c:\\program files\\sql server\\customer-part-2011.mdf\"")),
          FileMapping(FileId(6), FilePath("\"c:\\program files\\sql server\\customer-part-2012.mdf\""))
        )
    }

    "should return empty result if there is an issue during reading a file" in {
      fileReader.read("Non/Existing/FilePath.csv", fileMappingsParser).size shouldEqual 0
    }
  }
}
