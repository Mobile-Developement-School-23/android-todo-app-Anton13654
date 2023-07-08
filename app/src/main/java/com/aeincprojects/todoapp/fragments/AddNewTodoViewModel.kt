package com.aeincprojects.todoapp.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.data.models.TodoFromServer
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

    private val _itemToDo: MutableStateFlow<TodoFromServer?> = MutableStateFlow(null)
    val item = _itemToDo.asStateFlow()



    fun addNewTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addElementOnServer(TodoFromServer("1", "donyt", "low", 0, false, "0", 0,0, ""))
        }
    }


    fun takeId(newId: String){
        viewModelScope.launch(Dispatchers.IO) {
            id = newId
            if(id!=""){
                _itemToDo.value = repository.takeOneElement(id)
            }
        }
    }


    fun selectImportance(importance: String){

    }

    /*fun saveNewTodo(text: String, importance: Importance, data: String = ""){
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

    private fun saveNewTodo(text: String, importance: String, dataFinish: Long?){
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = TodoFromServer(System.currentTimeMillis().toString(), text, importance, dataFinish, false, "black", System.currentTimeMillis(), System.currentTimeMillis(), "me")
            repository.addElementOnServer(newItem)
        }
    }

    private fun updateTodo(text: String, importance: String, dataFinish: Long = 0){
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = TodoFromServer(System.currentTimeMillis().toString(), text, importance, dataFinish, false, "black", _itemToDo.value!!.created_at, System.currentTimeMillis(), "me")
            repository.updateElementOnServer(_itemToDo.value!!.id, newItem)
        }
    }

    fun saveTodo(text: String, importance: Importance, dataFinish: Long = 0){
        viewModelScope.launch(Dispatchers.IO) {
            val textImportance = when(importance){
                Importance.Low -> "low"
                Importance.Normal -> "normal"
                Importance.Urgent -> "urgent"
            }
            if(id!=""){
                updateTodo(text, textImportance, dataFinish)
            }else{
                saveNewTodo(text, textImportance, dataFinish)
            }
        }
    }



    fun deleteElement(){
        viewModelScope.launch(Dispatchers.IO) {
            if(id!=""){
                repository.deleteElementOnServer(id)
            }
        }
    }





}