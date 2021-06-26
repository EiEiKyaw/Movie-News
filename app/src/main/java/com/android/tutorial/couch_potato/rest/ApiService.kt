package com.android.tutorial.couch_potato.rest

import com.android.tutorial.couch_potato.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?")
    fun getByTitle(
        @Query("t") title: String,
        @Query("plot") plot: String
    ): Call<Movie>

    @GET("?")
    fun getByTitle(
        @Query("t") title: String,
        @Query("y") year: String,
        @Query("plot") plot: String
    ): Call<Movie>

    @GET("?")
    fun getById(
        @Query("i") id: String,
        @Query("plot") plot: String
    ): Call<Movie>


}