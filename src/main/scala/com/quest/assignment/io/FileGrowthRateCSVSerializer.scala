package com.quest.assignment.io

import com.quest.assignment.models.FileGrowthRate

class FileGrowthRateCSVSerializer extends CSVSerializer[FileGrowthRate] {

  override def columnHeaders: Seq[String] = Seq(
    "\"FileID\"",
    "\"Name\"",
    "\"Timestamp\"",
    "\"SizeInBytes\"",
    "\"GrowthRateInBytesPerHour\"")

  override def serialize(data: FileGrowthRate): String = Seq(
    s"${data.fileId.value}",
    s"${data.filePath.value}",
    s""""${data.dateTime.toString.replace("T", " ")}"""",
    s"${data.fileSize.value}",
    s"${data.growthRate.value}"
  ).mkString(delimiter)
}
