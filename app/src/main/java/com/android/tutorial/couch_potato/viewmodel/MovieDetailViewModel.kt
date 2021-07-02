package com.android.tutorial.couch_potato.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.rest.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : BaseViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val movieByTitle = MutableLiveData<Movie>()
    val movieById = MutableLiveData<Movie>()

    fun getByTitle(title: String, year: String){
        isLoading.value = true
        RestClient.getApiService()
            .getByTitle(title, year, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if(response.isSuccessful){
                        isLoading.value = false
                        response.body().let {
                            movieByTitle.value =  it
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    isLoading.value = false
                    Log.d("response", "......fail")
                }

            })
    }

    fun getById(imdbId: String){
        isLoading.value = true
        RestClient.getApiService()
            .getById(imdbId, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if(response.isSuccessful){
                        isLoading.value = false
                        response.body().let {
                            movieById.value =  it
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    isLoading.value = false
                    Log.d("response", "......fail")
                }

            })
    }

}