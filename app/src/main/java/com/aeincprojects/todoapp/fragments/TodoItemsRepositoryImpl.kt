package com.aeincprojects.todoapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.aeincprojects.todoapp.ServerApi
import com.aeincprojects.todoapp.data.database.TodoDao
import com.aeincprojects.todoapp.data.models.Element
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.data.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val context: Context,
    private val serverApi: ServerApi,
    private val sharedPreferences: SharedPreferences

    ): TodoItemsRepository {

    var items: MutableList<TodoItem> = mutableListOf(
            TodoItem("id1)", "Покушать", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id2)", "Поспать", Importance.Low, null, false, "18/06/2023", null),
            TodoItem("id3)", "Сходить в магазин", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id4)", "Погладить кошку", Importance.Urgent, null, false, "18/06/2023", null),
            TodoItem("id5)", "Начать делать диплом", Importance.Low, null, false, "18/06/2023", null),
            TodoItem("id6)", "Покушать ещё раз", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id7)", "Сделать алгоритмы", Importance.Urgent, null, false, "18/06/2023", null),
            TodoItem("id8)", "Купить покупки", Importance.Low, null, false, "18/06/2023", null),
            TodoItem("id9)", "Погладить кошку ещё раз, по направлению роста шерсти", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id10)", "Посмотреть лекцию", Importance.Urgent, null, false, "18/06/2023", null),
            TodoItem("id11)", "Поспать ещё раз", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id12)", "Выкинуть мусор", Importance.Low, null, false, "18/06/2023", null),
            TodoItem("id13)", "Проанализировать работу данной программы", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id14)", "Выкинуть мусор", Importance.Low, null, false, "18/06/2023", null),
            TodoItem("id15)", "Посмотреть лекцию", Importance.Urgent, null, false, "18/06/2023", null),
            TodoItem("id16)", "Поспать ещё раз", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id17)", "Выкинуть мусор", Importance.Low, null, false, "18/06/2023", null),
            TodoItem("id18)", "Проанализировать работу данной программы", Importance.Normal, null, false, "18/06/2023", null),
            TodoItem("id19)", "Выкинуть мусор", Importance.Low, null, false, "18/06/2023", null),

            )

    override suspend fun takeListTodo(): List<TodoItem> {
        return items.sortedBy { it.id }
    }

    override suspend fun addNewTodo(newTodoItem: TodoItem) {
        items = items.toMutableList()
        Log.i("NN", "new")
        items.add(newTodoItem)
    }

    override suspend fun updateStatus(todoItem1: TodoItem) {
        items = items.toMutableList()
        items.remove(todoItem1)
        items.add(todoItem1.copy(isDone = !todoItem1.isDone))

    }

    override suspend fun deleteElement(newId: String) {
        items = items.filter { it.id != newId }.toMutableList()
    }

    override suspend fun takeOneElement(newId: String): TodoFromServer? {
        return todoDao.getTodoById(newId)
    }

    override suspend fun updateElement(todoItemOld: TodoItem, todoItemNew: TodoItem) {
        items = items.toMutableList()
        items.remove(todoItemOld)
        items.add(todoItemNew)
    }

    override suspend fun getListFromServer(): List<TodoFromServer> {
        val listTodo =  serverApi.takeListFromServer().body()
        if (listTodo != null) {
            todoDao.addElements(listTodo.list)
        }
        return if (listTodo != null && listTodo.list.isNotEmpty()){
            listTodo.list
        }else{
           // todoDao.getListTodo()
            items.map{it.toWithoutRevision()}
        }
    }

    override suspend fun getElementFromServer(): TodoItem {
        TODO("Not yet implemented")
    }

    override suspend fun addElementOnServer(todo: TodoFromServer) {
        serverApi.addNewTodo(takeVersion(), Element(todo))
        updateVersion(takeVersion()+1)
    }

    override suspend fun updateElementOnServer(id: String, todo: TodoFromServer) {
        serverApi.editTodoInServer(takeVersion(), id, Element(todo))
        updateVersion(takeVersion()+1)
    }

    override suspend fun deleteElementOnServer(id: String): TodoItem {
        serverApi.deleteElement(takeVersion(), id)
        //todoDao.deleteTodo()
        updateVersion(takeVersion()+1)
        return TodoItem("", "", Importance.Low, "", false, "", "")
    }


    private fun takeVersion(): Int {
        return sharedPreferences.getInt("key", 11)
    }

    private fun updateVersion(newVersion: Int) {
        sharedPreferences.edit { putInt("key", newVersion) }
    }

}