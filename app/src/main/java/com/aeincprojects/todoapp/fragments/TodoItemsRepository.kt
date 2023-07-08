package com.aeincprojects.todoapp.fragments

import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.data.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.util.*

interface TodoItemsRepository {

    suspend fun takeListTodo(): List<TodoItem>

    suspend fun addNewTodo(newTodoItem: TodoItem)

    suspend fun updateStatus(todoItem: TodoItem)

    suspend fun deleteElement(id: String)

    suspend fun takeOneElement(id: String): TodoFromServer?

    suspend fun updateElement(_oldItem: TodoItem, todoItem: TodoItem)

    suspend fun getListFromServer(): List<TodoFromServer>

    suspend fun getElementFromServer(): TodoItem

    suspend fun addElementOnServer(todo: TodoFromServer)

    suspend fun updateElementOnServer(id: String, todo: TodoFromServer)

    suspend fun deleteElementOnServer(id: String): TodoItem

}