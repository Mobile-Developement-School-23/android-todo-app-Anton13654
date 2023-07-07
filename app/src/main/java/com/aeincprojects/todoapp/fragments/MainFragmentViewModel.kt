package com.aeincprojects.todoapp.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.data.models.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: TodoItemsRepository

): ViewModel() {

    private val _items: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val items = _items.asStateFlow()

    init {
        //sendItems()
        getListTodo()
    }

    fun getListTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            _items.value = repository.getListFromServer()
        }
    }

   /* fun sendItems(){
        viewModelScope.launch(Dispatchers.IO) {
            _items.value = repository.takeListTodo()
        }
    }

    */
/*
    fun updateStatusTodo(item: TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStatus(item)
            sendItems()
        }
    }

 */

    /*
    fun deleteElement(position: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteElement(_items.value[position].id)
            sendItems()
        }
    }

     */

}