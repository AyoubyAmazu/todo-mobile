package com.example.todo_lab.api

import com.example.todo_lab.data.Todo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos/1")
    fun getTodo(): Call<Todo>  // ⚠️ Pas suspend, pas de coroutine
}