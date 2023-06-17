package com.aeincprojects.todoapp.fragments

import androidx.lifecycle.ViewModel
import com.aeincprojects.todoapp.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.util.*

class AddNewTodoViewModel(
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl

): ViewModel() {
    var id: String = ""

    private val _itemToDo: MutableStateFlow<TodoItem?> = MutableStateFlow(null)
    val item = _itemToDo.asStateFlow()

    fun takeId(newId: String){
        id = newId
        if(id!=""){
            _itemToDo.value = repository.takeOneElement(id)
        }
    }

    fun selectImportance(importance: String){

    }

    fun saveNewTodo(text: String, importance: Importance, data: String = ""){
        lateinit var newItem: TodoItem
        if(_itemToDo.value==null){
            repository.addNewTodo(TodoItem(System.currentTimeMillis().toString(), text, importance, data, false, getCurrentData(), null))
        }else{
            newItem = TodoItem(_itemToDo.value!!.id, text, importance, data, _itemToDo.value!!.isDone, _itemToDo.value!!.dateCreation, getCurrentData())
            repository.updateElement(_itemToDo.value!!, newItem)
        }

    }

    fun getCurrentData(): String{
        val dataCreation = LocalDateTime.now()
        val day = dataCreation.dayOfMonth
        val month = dataCreation.monthValue
        var dayString = ""
        var mountString = ""
        if(day<10) {dayString = "0$day" }
        if(month<10) {mountString = "0$mountString" }
        return "$dayString/$mountString/${dataCreation.year}"
    }



    fun deleteElement(){
        if(id!=""){
            repository.deleteElement(id)
        }
    }



}