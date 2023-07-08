package com.aeincprojects.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
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
){

    fun toWithoutRevision() = TodoFromServer(id, textToDo, "low", 0, isDone, "black", 1, 1, "0")
}
