package com.aeincprojects.todoapp.fragments

import androidx.lifecycle.ViewModel
import com.aeincprojects.todoapp.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.util.*

class AddNewTodoViewModel(

): ViewModel() {

    var dateFinish: Date = Date()

    fun saveDateFinish(date: Date){
        dateFinish = date
    }

    fun selectImportance(importance: String){

    }

    fun saveNewTodo(text: String, importance: Importance, data: Date, dataCreation: String){

    }


}