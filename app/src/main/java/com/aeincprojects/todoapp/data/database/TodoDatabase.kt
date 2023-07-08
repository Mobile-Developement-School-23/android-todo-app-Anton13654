package com.aeincprojects.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.data.models.TodoItem

@Database(entities = [TodoFromServer::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object{
var db: TodoDatabase? = null
        fun buildDB(context: Context): TodoDatabase{
            db = Room.databaseBuilder(
                context,
                TodoDatabase::class.java,
                "myDatabase"
            ).build()
            return db!!
        }
    }
}


