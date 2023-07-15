package com.aeincprojects.todoapp.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.aeincprojects.todoapp.data.TodoItemsRepositoryImpl
import com.aeincprojects.todoapp.data.api.HeaderInterceptor
import com.aeincprojects.todoapp.data.api.ServerApi
import com.aeincprojects.todoapp.data.database.TodoDao
import com.aeincprojects.todoapp.data.database.TodoDatabase
import com.aeincprojects.todoapp.domain.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideToDoRepository(todoDao: TodoDao, @ApplicationContext context: Context, serverApi: ServerApi, sharedPreferences: SharedPreferences, themeSharPref: SharedPreferences): TodoItemsRepository = TodoItemsRepositoryImpl(todoDao, context, serverApi, sharedPreferences )

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
    fun provideServerApi(okHttpClient: OkHttpClient): ServerApi = Retrofit.Builder()
        .baseUrl(ServerApi.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServerApi::class.java)


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideHeaderInterceptor( ):HeaderInterceptor = HeaderInterceptor("Bearer unappetizing")

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences("version", Context.MODE_PRIVATE)

}