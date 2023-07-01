package com.aeincprojects.todoapp.data.models

class ListTodoFromServer (
    val status: String,
    val list: List<TodoFromServer>,
    val revision: Int
        )