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
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore

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
                Log.d("response", "favorite movie list size............." + it.result!!.size())
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
}