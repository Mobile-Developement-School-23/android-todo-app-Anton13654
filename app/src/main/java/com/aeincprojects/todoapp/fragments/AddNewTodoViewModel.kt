package com.aeincprojects.todoapp.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.data.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNewTodoViewModel @Inject constructor(
    private val repository: TodoItemsRepository

): ViewModel() {
    var id: String = ""

    private val _itemToDo: MutableStateFlow<TodoItem?> = MutableStateFlow(null)
    val item = _itemToDo.asStateFlow()



    fun addNewTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addElementOnServer(TodoItem("0", "Не спать до утра", Importance.Low, "0", false, "0", "0"))
        }
    }

/*
    fun takeId(newId: String){
        id = newId
        if(id!=""){
            _itemToDo.value = repository.takeOneElement(id)
        }
    }

 */

    fun selectImportance(importance: String){

    }
/*
    fun saveNewTodo(text: String, importance: Importance, data: String = ""){
        viewModelScope.launch(Dispatchers.IO) {
            lateinit var newItem: TodoItem
            if(_itemToDo.value==null){
                repository.addNewTodo(TodoItem(System.currentTimeMillis().toString(), text, importance, data, false, getCurrentData(), null))
            }else{
                newItem = TodoItem(_itemToDo.value!!.id, text, importance, data, _itemToDo.value!!.isDone, _itemToDo.value!!.dateCreation, getCurrentData())
                repository.updateElement(_itemToDo.value!!, newItem)
            }
        }
    }

 */

    private fun getCurrentData(): String{
        val dataCreation = LocalDateTime.now()
        val day = dataCreation.dayOfMonth
        val month = dataCreation.monthValue
        var dayString = ""
        var mountString = ""
        if(day<10) {dayString = "0$day" }
        if(month<10) {mountString = "0$mountString" }
        return "$dayString/$mountString/${dataCreation.year}"
    }


/*
    fun deleteElement(){
        viewModelScope.launch(Dispatchers.IO) {
            if(id!=""){
                repository.deleteElement(id)
            }
        }
    }

 */



}