package com.teamplay.core.function.date

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class DateUtil {
    fun localDateToDate(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    fun getStartDayByLocalDate(localDate: LocalDate): Date {
        return localDateToDate(localDate.atStartOfDay().toLocalDate())
    }

    fun getEndDayByLocalDate(localDate: LocalDate): Date {
        return localDateToDate(localDate.atStartOfDay().plusDays(1).minusSeconds(1).toLocalDate())
    }
}
