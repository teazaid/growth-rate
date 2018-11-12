package com.quest.assignment.models

import java.time.LocalDateTime

case class FileStats(fileId: FileId,
                     localDateTime: LocalDateTime,
                     fileSize: FileSize)
