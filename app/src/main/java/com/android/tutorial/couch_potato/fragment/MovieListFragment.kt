package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.MovieListFragAdapter
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.model.MovieHistory
import com.android.tutorial.couch_potato.rest.RestClient
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListFragment : Fragment(), MovieListener {

    private lateinit var adapter: MovieListFragAdapter
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_movie_list, container, false)
        adapter = MovieListFragAdapter(this)
        viewModel = MovieDetailViewModel()
        val rvMovies: RecyclerView = view.findViewById(R.id.rvLatestMovies)
        setMovie("batman-begins", "2005")
        setMovie("enola", "2020")
        setMovie("frozen", "2020")
        setMovie("justice", "2021")
        setMovie("wonder", "2020")
        setMovie("shazam", "2019")
        rvMovies.adapter = adapter
        rvMovies.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    private fun setMovie(title: String, year: String) {
        RestClient.getApiService()
            .getByTitle(title, year, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if (response.isSuccessful) {
                        Log.d("response", "......success")
                        response.body()?.let { movie ->
                            adapter.setNewData(movie)
                            Log.d("response", "movie............." + movie.title)
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("response", "......fail")
                }
            })
    }

    override fun onFavoriteClicked(movie: MovieHistory) {
        manageFavoriteMovie(movie)
    }

    override fun onBookmarkClicked(movie: MovieHistory) {
        manageBookmarkMovie(movie)
    }

    private fun manageFavoriteMovie(movie: MovieHistory) {
        val collection = FirebaseFirestore.getInstance().collection("favorite-movies")
        val data: MutableMap<String, Any> = HashMap()
        data["imdbId"] = movie.imdbId
        data["isFavorite"] = movie.isFavorite
        var isValid = false
        var documentId = ""
        collection.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("imdbId").equals(movie.imdbId)) {
                        isValid = true
                        documentId = document.id
                    }
                }
                if (!isValid) {
                    collection.add(data)
                } else {
                    collection.document(documentId).update("isFavorite", movie.isFavorite)
                }
            }
        }
    }

    private fun manageBookmarkMovie(movie: MovieHistory) {
        val collection = FirebaseFirestore.getInstance().collection("bookmark-movies")
        val data: MutableMap<String, Any> = HashMap()
        data["imdbId"] = movie.imdbId
        data["isBookmark"] = movie.isFavorite
        var isValid = false
        var documentId = ""
        collection.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("imdbId").equals(movie.imdbId)) {
                        isValid = true
                        documentId = document.id
                    }
                }
                if (!isValid) {
                    collection.add(data)
                } else {
                    collection.document(documentId).update("isBookmark", movie.isBookmark)
                }
            }
        }
    }

}
