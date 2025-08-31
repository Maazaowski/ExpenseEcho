package com.expenseecho.data.converter;

import androidx.room.TypeConverter;
import java.time.LocalDate;
import java.time.YearMonth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0007J\u0014\u0010\n\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004H\u0007J\u0014\u0010\f\u001a\u0004\u0018\u00010\t2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/expenseecho/data/converter/DateConverters;", "", "()V", "fromLocalDate", "", "date", "Ljava/time/LocalDate;", "fromYearMonth", "yearMonth", "Ljava/time/YearMonth;", "toLocalDate", "dateString", "toYearMonth", "yearMonthString", "app_debug"})
public final class DateConverters {
    
    public DateConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromLocalDate(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate date) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate toLocalDate(@org.jetbrains.annotations.Nullable()
    java.lang.String dateString) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromYearMonth(@org.jetbrains.annotations.Nullable()
    java.time.YearMonth yearMonth) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.YearMonth toYearMonth(@org.jetbrains.annotations.Nullable()
    java.lang.String yearMonthString) {
        return null;
    }
}