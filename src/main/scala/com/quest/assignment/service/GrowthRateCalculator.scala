package com.quest.assignment.service

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import com.quest.assignment.models.{FileStats, GrowthRate}

class GrowthRateCalculator {
  private val millisInHour = 3600000

  private[service] def millisDiff(startDate: LocalDateTime, endDate: LocalDateTime): Long =
    ChronoUnit.MILLIS.between(startDate, endDate)

  def ratePerHour(currentStats: FileStats, nextStats: FileStats): GrowthRate = {
    val millis = millisDiff(currentStats.localDateTime, nextStats.localDateTime)

    val rate = BigDecimal((nextStats.fileSize.value - currentStats.fileSize.value)) / millis * millisInHour
    GrowthRate(rate.setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble)
  }
}