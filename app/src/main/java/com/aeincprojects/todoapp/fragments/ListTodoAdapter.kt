package com.aeincprojects.todoapp.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.aeincprojects.todoapp.R
import com.aeincprojects.todoapp.models.TodoItem
import com.aeincprojects.todoapp.util.Importance


class ListTodoAdapter(val onClick: (TodoItem)-> Unit): RecyclerView.Adapter<ListTodoAdapter.ListTodoViewHolder>() {

    var items: List<TodoItem> = listOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.model_of_todo_list, parent, false)
        return ListTodoViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ListTodoViewHolder, position: Int) {
        var currentElement = items[position]
        var colorGreen = ColorStateList.valueOf(Color.RED)
        var colorBlack = ColorStateList.valueOf(Color.BLACK)
        var colorRed = ColorStateList.valueOf(Color.GREEN)

        holder.checkBox.text = currentElement.textToDo
        holder.checkBox.buttonTintList = when(currentElement.importance){
            Importance.Low -> colorGreen
            Importance.Normal -> colorBlack
            Importance.Urgent -> colorRed
        }
        holder.checkBox.isChecked = currentElement.isDone
        holder.checkBox.setOnClickListener {
            onClick(currentElement)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ListTodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val checkBox: CheckBox = itemView.findViewById(R.id.todo_checkbox)
        val infoButton: ImageButton = itemView.findViewById(R.id.todo_info)
    }
}