package com.aeincprojects.todoapp

import com.aeincprojects.todoapp.data.models.ElementTodoFromServer
import com.aeincprojects.todoapp.data.models.ElementTodoWithoutRevision
import com.aeincprojects.todoapp.data.models.ListTodoFromServer
import com.aeincprojects.todoapp.data.models.ListTodoWithoutRevision
import com.aeincprojects.todoapp.data.models.TodoFromServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServerApi {


    @GET("list")
    suspend fun takeListFromServer(@Header("Authorization") token: String): Response<ListTodoFromServer>

    @PATCH("list")
    suspend fun updateListServer (@Header("Authorization") token: String, @Body usersStory: ListTodoWithoutRevision): Response<ListTodoFromServer>

    @GET("list/{id}")
    suspend fun takeTodoFromServer(@Header("Authorization") token: String, @Path("id") id: String): Response<ElementTodoFromServer>

    @POST("list")
    suspend fun addNewTodo(@HeaderMap headers: HashMap<String, String>, @Body usersTodo: TodoFromServer): Response<ElementTodoFromServer>

    @PUT("list/{id}")
    suspend fun editTodoInServer(@Header("Authorization") token: String, @Path("id") id: String, @Body usersTodo: ElementTodoWithoutRevision): Response<ElementTodoFromServer>

    @DELETE("list/{id}")
    suspend fun deleteNotConfirm(@Header("Authorization") token: String, @Path("id") id: String): Response<ElementTodoFromServer>


    companion object{
        const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
        val test = TodoFromServer("id1", "textFirst", "low", null, false, null, 0, 0, "007")
    }
}