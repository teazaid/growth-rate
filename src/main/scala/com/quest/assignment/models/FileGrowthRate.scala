package com.quest.assignment.models

import java.time.LocalDateTime

case class FileGrowthRate(fileId: FileId,
                          filePath: FilePath,
                          dateTime: LocalDateTime,
                          fileSize: FileSize,
                          growthRate: GrowthRate)
