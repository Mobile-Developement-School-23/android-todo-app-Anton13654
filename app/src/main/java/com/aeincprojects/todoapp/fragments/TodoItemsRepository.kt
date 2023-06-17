package com.aeincprojects.todoapp.fragments

import com.aeincprojects.todoapp.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.util.*

interface TodoItemsRepository {

    fun takeListTodo(): List<TodoItem>

    fun addNewTodo(newTodoItem: TodoItem)

    fun updateStatus(todoItem: TodoItem)

    fun deleteElement(id: String)
}