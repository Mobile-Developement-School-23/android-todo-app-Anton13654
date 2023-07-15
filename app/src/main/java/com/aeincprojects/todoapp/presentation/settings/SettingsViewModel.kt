package com.aeincprojects.todoapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeincprojects.todoapp.domain.TodoItemsRepository
import com.aeincprojects.todoapp.util.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: TodoItemsRepository
): ViewModel() {

    private val _theme: MutableStateFlow<Theme?> = MutableStateFlow(null)
    val theme = _theme.asStateFlow()

    init {
        takeTheme()
    }

    private fun takeTheme(){
        viewModelScope.launch(Dispatchers.IO) {
            _theme.value = repository.takeTheme()
        }
    }

    fun updateTheme(theme: Theme){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTheme(theme)
            takeTheme()
        }
    }
}