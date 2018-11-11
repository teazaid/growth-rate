package com.quest.assignment.models

import java.time.LocalDateTime

case class FileGrowthRate(fileId: FileId,
  filePath: FilePath,
  localDateTime: LocalDateTime,
  fileSize: FileSize,
  growthRate: GrowthRate)
