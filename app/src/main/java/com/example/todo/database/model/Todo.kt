package com.example.todo.database.model

import android.provider.ContactsContract

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date
@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id:Int?=null,
    @ColumnInfo
    var name :String?=null,
    @ColumnInfo
    var details:String?=null,
    @ColumnInfo
    var date: Date?=null,
    @ColumnInfo
    var isDone:Boolean=false

):Serializable


