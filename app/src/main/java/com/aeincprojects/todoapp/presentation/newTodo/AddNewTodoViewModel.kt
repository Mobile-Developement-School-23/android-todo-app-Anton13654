package com.aeincprojects.todoapp.presentation.newTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.domain.TodoItemsRepository
import com.aeincprojects.todoapp.util.Importance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewTodoViewModel @Inject constructor(
    private val repository: TodoItemsRepository

): ViewModel() {
    var id: String = ""

    private val _itemToDo: MutableStateFlow<TodoFromServer?> = MutableStateFlow(null)
    val item = _itemToDo.asStateFlow()

    fun takeId(newId: String){
        viewModelScope.launch(Dispatchers.IO) {
            id = newId
            if(id!=""){
                _itemToDo.value = repository.takeOneElement(id)
            }
        }
    }


    fun selectImportance(importance: String){}

    private fun saveNewTodo(text: String, importance: String, dataFinish: Long?){
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = TodoFromServer(System.currentTimeMillis().toString(), text, importance, dataFinish, false, "0", System.currentTimeMillis(), System.currentTimeMillis(), "me")
            repository.addElementOnServer(newItem)
        }
    }

    private fun updateTodo(text: String, importance: String, dataFinish: Long = 0){
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = TodoFromServer(_itemToDo.value!!.id, text, importance, dataFinish, false, "black", _itemToDo.value!!.created_at, System.currentTimeMillis(), "me")
            repository.updateElementOnServer(_itemToDo.value!!.id, newItem)
        }
    }

    fun saveTodo(text: String, importance: Importance, dataFinish: Long = 0){
        viewModelScope.launch(Dispatchers.IO) {
            val textImportance = when(importance){
                Importance.Low    -> "low"
                Importance.Normal -> "basic"
                Importance.Urgent -> "important"
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