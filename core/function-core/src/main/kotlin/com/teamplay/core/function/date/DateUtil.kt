package com.teamplay.core.function.date

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class DateUtil {
    fun localDateTimeToDate(localDateTime: LocalDateTime): Date {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }

    fun getStartDayByLocalDate(localDate: LocalDate): Date {
        return localDateTimeToDate(localDate.atStartOfDay())
    }

    fun getEndDayByLocalDate(localDate: LocalDate): Date {
        return localDateTimeToDate(localDate.atStartOfDay().plusDays(1).minusSeconds(1))
    }
}
