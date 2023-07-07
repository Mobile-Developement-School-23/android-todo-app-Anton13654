package com.aeincprojects.todoapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aeincprojects.todoapp.data.models.TodoItem

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addElements(listTodo: List<TodoItem>)

    @Query("SELECT * FROM usersTodo")
    fun getListTodo(): List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewElement(newTodo: TodoItem)

    @Update
    fun updateTodo(newTodo: TodoItem)

    @Delete
    fun deleteTodo(todoItem: TodoItem)

}