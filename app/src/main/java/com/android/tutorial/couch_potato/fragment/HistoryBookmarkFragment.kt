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
import com.android.tutorial.couch_potato.adapter.BookmarkMovieListAdapter
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.rest.RestClient
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryBookmarkFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var adapter: BookmarkMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_history_bookmark, container, false)
        adapter = BookmarkMovieListAdapter()
        viewModel = MovieDetailViewModel()
        val rvMovies: RecyclerView = view.findViewById(R.id.rvBookmarkMovies)
        getBookmarkMovies()
        rvMovies.adapter = adapter
        rvMovies.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        return view
    }

    private fun getBookmarkMovies() {
        val collection = FirebaseFirestore.getInstance().collection("bookmark-movies")
        collection.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("isBookmark") == true) {
                        getMovie(document.data.getValue("imdbId").toString())
                    }
                }
            }
        }
    }

    private fun getMovie(imdbId: String) {
        RestClient.getApiService()
            .getById(imdbId, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { movie ->
                            adapter.setNewData(movie)
                            Log.d("bmresponse", "movie............." + movie.title)
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("bmresponse", "......fail")
                }
            })
    }

}