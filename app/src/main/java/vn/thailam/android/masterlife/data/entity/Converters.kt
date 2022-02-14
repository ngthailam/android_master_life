package vn.thailam.android.masterlife.data.entity

import androidx.room.TypeConverter
import java.util.*

object Converters {
    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): Date? {
        return timeStamp?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}