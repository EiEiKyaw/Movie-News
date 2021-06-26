package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.FavoriteMovieListAdapter
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.rest.RestClient
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFavoriteFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var adapter: FavoriteMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_history_favorite, container, false)
        adapter = FavoriteMovieListAdapter()
        viewModel = MovieDetailViewModel()
        val rvMovies: RecyclerView = view.findViewById(R.id.rvFavoriteMovies)
        FirebaseFirestore.getInstance().collection("favorite-movies").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("isFavorite") == true) {
                        viewModel.getById(document.data.getValue("imdbId").toString())
                        viewModel.movieById.observe(viewLifecycleOwner, Observer { movie ->
                                adapter.setNewData(movie)
                        })
                    }
                }
            }
        }
        rvMovies.adapter = adapter
        rvMovies.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        return view
    }

    private fun getFavMovies() {
        val collection = FirebaseFirestore.getInstance().collection("favorite-movies")
        collection.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("isFavorite") == true) {
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
                            Log.d("favresponse", "movie............." + movie.title)
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("favresponse", "......fail")
                }
            })
    }
}