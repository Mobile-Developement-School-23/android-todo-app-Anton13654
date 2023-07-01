package com.aeincprojects.todoapp.data.models

import com.aeincprojects.todoapp.util.Importance

class TodoFromServer (
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    val createdAt: Long,
    val changingAt: Long,
    val lastUpdatedBy: String
        )

{
    fun toTodoItem() = TodoItem(id, text, Importance.Low, deadline.toString(), done, createdAt.toString(), changingAt.toString())
}