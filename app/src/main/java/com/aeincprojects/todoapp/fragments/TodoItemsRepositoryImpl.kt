package com.aeincprojects.todoapp.fragments

import android.util.Log
import com.aeincprojects.todoapp.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.util.*

object TodoItemsRepositoryImpl: TodoItemsRepository {


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

    override fun takeListTodo(): List<TodoItem> {
        return items.sortedBy { it.id }
    }

    override fun addNewTodo(newTodoItem: TodoItem) {
        items = items.toMutableList()
        Log.i("NN", "new")
        items.add(newTodoItem)
    }

    override fun updateStatus(todoItem1: TodoItem) {
        items = items.toMutableList()
        items.remove(todoItem1)
        items.add(todoItem1.copy(isDone = !todoItem1.isDone))

    }

    override fun deleteElement(newId: String) {
        items = items.filter { it.id != newId }.toMutableList()
    }

    override fun takeOneElement(newId: String): TodoItem? {
        val itemsFiltred = items.filter { it.id == newId }.toMutableList()
        if(itemsFiltred.isNotEmpty()){
            return itemsFiltred[0]
        }else return null
    }

    override fun updateElement(todoItemOld: TodoItem, todoItemNew: TodoItem) {
        items = items.toMutableList()
        items.remove(todoItemOld)
        items.add(todoItemNew)
    }
}