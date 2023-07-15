package com.aeincprojects.todoapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.data.models.TodoItem

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addElements(listTodo: List<TodoFromServer>)

    @Query("SELECT * FROM Todos")
    fun getListTodo(): List<TodoFromServer>

    @Query("SELECT * FROM Todos WHERE id= :id")
    fun getTodoById(id: String): TodoFromServer

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewElement(newTodo: TodoFromServer)

    @Update
    fun updateTodo(newTodo: TodoFromServer)

    @Query("DELETE FROM Todos WHERE id= :id")
    fun deleteTodo(id: String)

}