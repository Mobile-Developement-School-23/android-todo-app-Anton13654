package com.aeincprojects.todoapp.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.aeincprojects.todoapp.data.api.ServerApi
import com.aeincprojects.todoapp.data.database.TodoDao
import com.aeincprojects.todoapp.data.models.Element
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.domain.TodoItemsRepository
import com.aeincprojects.todoapp.util.Theme
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val context: Context,
    private val serverApi: ServerApi,
    private val sharedPreferences: SharedPreferences

    ): TodoItemsRepository {

    override suspend fun takeOneElement(newId: String): TodoFromServer? {
        return todoDao.getTodoById(newId)
    }


    override suspend fun getListFromServer(): List<TodoFromServer> {
            return try {
                val listTodo =  serverApi.takeListFromServer().body()
                if (listTodo != null) {
                    todoDao.addElements(listTodo.list)
                    updateVersion(listTodo.revision)
                }
                return if (listTodo != null && listTodo.list.isNotEmpty()){
                    listTodo.list
                }else{
                    todoDao.getListTodo()
                }
            }catch (e: Exception) {
                Log.i("network", e.toString())
                todoDao.getListTodo()
            }
    }

    override suspend fun getElementFromServer(id: String) {
        serverApi.takeTodoFromServer(id).body()?.element
    }

    override suspend fun addElementOnServer(todo: TodoFromServer) {
        try {
            serverApi.addNewTodo(takeVersion(), Element(todo))
            todoDao.addElements(listOf(todo))
            updateVersion(takeVersion()+1)
        }catch (e: Exception){
            Log.i("network", e.toString())
            todoDao.addElements(listOf(todo))
        }

    }

    override suspend fun updateElementOnServer(id: String, todo: TodoFromServer) {
        try{
            serverApi.editTodoInServer(takeVersion(), id, Element(todo))
            todoDao.updateTodo(todo)
            updateVersion(takeVersion()+1)
        }catch (e: Exception){
            Log.i("network", e.toString())
            todoDao.updateTodo(todo)
        }

    }

    override suspend fun deleteElementOnServer(id: String) {
        try {
            serverApi.deleteElement(takeVersion(), id)
            todoDao.deleteTodo(id)
            updateVersion(takeVersion()+1)
        }catch (e: Exception){
            Log.i("network", e.toString())
            todoDao.deleteTodo(id)
        }

    }

    private fun takeVersion(): Int {
        return sharedPreferences.getInt("key", 17)
    }

    private fun updateVersion(newVersion: Int) {
        sharedPreferences.edit { putInt("key", newVersion) }
    }

    override suspend fun takeTheme(): Theme{
        return Theme.valueOf(sharedPreferences.getString("theme", Theme.System.name)!!)
    }

    override suspend fun updateTheme(theme: Theme) {
        sharedPreferences.edit { putString("theme", theme.name) }
    }
}