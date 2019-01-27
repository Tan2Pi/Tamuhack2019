package com.unnamed.tamuhack2019

import retrofit2.Call
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EmployeeAPIService {


    @Headers("Accept: application/json")
    @GET("users/{id}")
    fun getTasks(@Path("id") userId: Int) : Call<User>

    @Headers("Accept: application/json")
    @POST("completed/{id}")
    fun completedTask(@Path("id") taskId: Int) : Call<ResponseBody>

    @POST("awaiting/{id}")
    fun userDone(@Path("id") userId: Int)

    @POST("user_needs_help/{id}")
    fun helpUser(@Path("id") userId: Int)

    @GET("tasks_count")
    fun tasksCompleted() : Call<Progress>

    companion object {
        fun create(): EmployeeAPIService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://tamuhack2019-todo.appspot.com/")
                .build()

            return retrofit.create(EmployeeAPIService::class.java)
        }
    }


//    @GET("/getTimeRemaining")
//    fun getTimeRemaining(@Query)

}