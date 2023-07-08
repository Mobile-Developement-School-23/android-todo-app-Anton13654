package com.aeincprojects.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aeincprojects.todoapp.util.Importance

@Entity(tableName = "Todos")
class TodoFromServer (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String
        )

{
    fun toTodoItem() = TodoItem(id, text, Importance.Low, deadline.toString(), done, created_at.toString(), changed_at.toString())
}