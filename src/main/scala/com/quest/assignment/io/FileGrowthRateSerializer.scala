package com.quest.assignment.io

import com.quest.assignment.models.FileGrowthRate

class FileGrowthRateSerializer extends Serializer[FileGrowthRate] {
  override def serialize(data: FileGrowthRate): String = {
    s"""${ data.fileId.value },${ data.filePath.value },"${ data.localDateTime.toString.replace("T", " ") }",${ data.fileSize.value },${ data.growthRate.value }"""
  }
}
