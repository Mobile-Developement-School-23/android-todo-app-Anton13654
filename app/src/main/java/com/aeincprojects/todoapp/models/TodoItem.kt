package com.aeincprojects.todoapp.models

import com.aeincprojects.todoapp.util.Importance
import java.util.Date

data class TodoItem(
    val id: String,
    val textToDo: String,
    val importance: Importance,
    val deadlineToDo: String?,
    val isDone: Boolean,
    val dateCreation: String,
    val dateChange: String?
)
