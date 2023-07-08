package com.aeincprojects.todoapp

import com.aeincprojects.todoapp.data.models.*
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
    suspend fun takeListFromServer(): Response<ListTodoFromServer>

    @PATCH("list")
    suspend fun updateListServer (@Header("X-Last-Known-Revision") token: Int, @Body usersStory: ListTodoWithoutRevision): Response<ListTodoFromServer>

    @GET("list/{id}")
    suspend fun takeTodoFromServer(@Path("id") id: String): Response<ElementTodoFromServer>

    @POST("list")
    suspend fun addNewTodo(@Header("X-Last-Known-Revision") token: Int, @Body usersTodo: Element): Response<ElementTodoFromServer>

    @PUT("list/{id}")
    suspend fun editTodoInServer(@Header("X-Last-Known-Revision") token: Int, @Path("id") id: String, @Body usersTodo: Element): Response<ElementTodoFromServer>

    @DELETE("list/{id}")
    suspend fun deleteElement(@Header("X-Last-Known-Revision") token: Int, @Path("id") id: String): Response<ElementTodoFromServer>


    companion object{
        const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
        val test = TodoFromServer("id1", "textFirst", "low", null, false, null, 0, 0, "007")
    }
}