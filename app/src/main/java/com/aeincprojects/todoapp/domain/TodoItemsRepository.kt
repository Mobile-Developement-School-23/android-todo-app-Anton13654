package com.aeincprojects.todoapp.domain

import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.data.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.util.*

interface TodoItemsRepository {

    suspend fun takeOneElement(id: String): TodoFromServer?

    suspend fun getListFromServer(): List<TodoFromServer>

    suspend fun getElementFromServer(id: String)

    suspend fun addElementOnServer(todo: TodoFromServer)

    suspend fun updateElementOnServer(id: String, todo: TodoFromServer)

    suspend fun deleteElementOnServer(id: String)

    suspend fun updateListOnServer()

}