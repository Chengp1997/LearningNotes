package edu.vt.cs5254.dreamcatcher.database

import androidx.room.TypeConverter
import java.util.*

// database can only store primitive/enum/UIUD, other types need to be converted to store
class DreamTypeConverters {
    //convert date to long to store in db
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long):Date {
        return Date(millisSinceEpoch)
    }
}