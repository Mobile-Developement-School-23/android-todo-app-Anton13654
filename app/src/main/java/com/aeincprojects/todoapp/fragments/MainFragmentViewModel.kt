package com.aeincprojects.todoapp.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.models.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainFragmentViewModel(
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl

): ViewModel() {

    private val _items: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val items = _items.asStateFlow()

    init {
        sendItems()
    }

    private fun sendItems(){
        viewModelScope.launch(Dispatchers.IO) {
            _items.value = repository.takeListTodo()
        }
    }

    fun updateStatusTodo(item: TodoItem){
        repository.updateStatus(item)
        sendItems()
    }

    fun deleteElement(position: Int){
        repository.deleteElement(_items.value[position].id)
        sendItems()
    }

}