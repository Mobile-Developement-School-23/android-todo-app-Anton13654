package com.aeincprojects.todoapp.fragments

import com.aeincprojects.todoapp.data.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.util.*

interface TodoItemsRepository {

    fun takeListTodo(): List<TodoItem>

    fun addNewTodo(newTodoItem: TodoItem)

    fun updateStatus(todoItem: TodoItem)

    fun deleteElement(id: String)

    fun takeOneElement(id: String): TodoItem?

    fun updateElement(_oldItem: TodoItem, todoItem: TodoItem)

    suspend fun getListFromServer(): List<TodoItem>

    suspend fun getElementFromServer(): TodoItem

    suspend fun addElementOnServer(todoItem: TodoItem)

    suspend fun updateElementOnServer(id: String, todoItem: TodoItem): TodoItem

    suspend fun deleteElementOnServer(id: String, todoItem: TodoItem): TodoItem

}