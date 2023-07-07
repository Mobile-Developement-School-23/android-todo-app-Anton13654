package com.aeincprojects.todoapp.data

import android.content.Context
import androidx.room.Room
import com.aeincprojects.todoapp.ServerApi
import com.aeincprojects.todoapp.data.database.TodoDao
import com.aeincprojects.todoapp.data.database.TodoDatabase
import com.aeincprojects.todoapp.fragments.TodoItemsRepository
import com.aeincprojects.todoapp.fragments.TodoItemsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideToDoRepository(todoDao: TodoDao, @ApplicationContext context: Context, serverApi: ServerApi): TodoItemsRepository = TodoItemsRepositoryImpl(todoDao, context, serverApi )

    @Provides
    @Singleton
    fun provideToDoDatabase(@ApplicationContext context: Context): TodoDatabase = Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        "MyDatabase").build()

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()

    @Provides
    @Singleton
    fun provideServerApi(): ServerApi = Retrofit.Builder()
        .baseUrl(ServerApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServerApi::class.java)
}