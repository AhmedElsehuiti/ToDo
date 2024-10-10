package com.example.todo.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.database.model.Todo
import java.util.Date

@Dao
interface TodoDao {
    @Insert
    fun addTodo(todo:Todo)
    @Update
    fun updateTodo(todo: Todo)
    @Delete
    fun deleteTodo(todo: Todo)
    @Query("select * from Todo")
    fun getAllTodo():MutableList<Todo>
    @Query("select * from Todo where date=:date")
    fun getTodoByDate(date: Date):MutableList<Todo>


}