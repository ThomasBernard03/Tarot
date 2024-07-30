package data.local

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(dateLong).toLocalDateTime(TimeZone.UTC)
    }

    @TypeConverter
    fun fromDate(date: LocalDateTime): Long {
        return date.toInstant(TimeZone.UTC).toEpochMilliseconds()
    }
}