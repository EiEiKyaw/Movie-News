package com.android.tutorial.couch_potato.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.rest.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {

    val movieByTitle = MutableLiveData<Movie>()
    val movieById = MutableLiveData<Movie>()

    fun getByTitle(title: String, year: String){
        RestClient.getApiService()
            .getByTitle(title, year, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if(response.isSuccessful){
                        response.body().let {
                            movieByTitle.value =  it
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("response", "......fail")
                }

            })
    }

    fun getById(imdbId: String){
        RestClient.getApiService()
            .getById(imdbId, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if(response.isSuccessful){
                        response.body().let {
                            movieById.value =  it
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("response", "......fail")
                }

            })
    }

}