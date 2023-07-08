package com.aeincprojects.todoapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.domain.TodoItemsRepository
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

    private val _items: MutableStateFlow<List<TodoFromServer>> = MutableStateFlow(emptyList())
    val items = _items.asStateFlow()

    init {
        getListTodo()
    }

    fun getListTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            _items.value = repository.getListFromServer()
        }
    }

    fun deleteElement(position: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteElementOnServer(_items.value[position].id)
            getListTodo()
        }
    }



}