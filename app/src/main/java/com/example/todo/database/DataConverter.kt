package com.example.todo.database

import androidx.room.TypeConverter
import java.util.*


class DataConverter {
    @TypeConverter
    fun fromDate(data: Date):Long{
        return data.time
    }
    @TypeConverter
    fun toDate(time:Long):Date{
        return  Date(time)
    }
}