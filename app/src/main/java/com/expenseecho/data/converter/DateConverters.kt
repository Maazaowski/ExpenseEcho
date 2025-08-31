package com.expenseecho.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.YearMonth

class DateConverters {
    
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }
    
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
    
    @TypeConverter
    fun fromYearMonth(yearMonth: YearMonth?): String? {
        return yearMonth?.toString()
    }
    
    @TypeConverter
    fun toYearMonth(yearMonthString: String?): YearMonth? {
        return yearMonthString?.let { YearMonth.parse(it) }
    }
}
