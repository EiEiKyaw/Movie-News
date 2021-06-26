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
        FirebaseFirestore.getInstance().collection("bookmark-movies").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("isBookmark") == true) {
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