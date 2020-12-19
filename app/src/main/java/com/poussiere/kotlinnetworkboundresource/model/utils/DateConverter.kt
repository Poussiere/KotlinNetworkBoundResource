package com.poussiere.kotlinnetworkboundresource.model.utils

import androidx.room.TypeConverter
import org.joda.time.LocalDateTime

class DateConverter {
        @TypeConverter
        fun toDate(dateString: String?): LocalDateTime? {
            return if (dateString == null) {
                null
            } else {
                LocalDateTime.parse(dateString)
            }
        }

        @TypeConverter
        fun toDateString(date: LocalDateTime?): String? {
            return if (date == null) {
                null
            } else {
                date.toString()
            }
        }
    }