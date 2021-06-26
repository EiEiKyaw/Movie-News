package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.MovieListFragAdapter
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.MovieHistory
import com.android.tutorial.couch_potato.util.ManageMovieHistory
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore

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
        viewModel.getByTitle(title, year)
        viewModel.movieByTitle.observe(viewLifecycleOwner, { movie ->
            adapter.setNewData(movie)
        })
    }

    override fun onFavoriteClicked(movie: MovieHistory) {
        ManageMovieHistory.manage(movie, "favorite-movies")
    }

    override fun onBookmarkClicked(movie: MovieHistory) {
        ManageMovieHistory.manage(movie, "bookmark-movies")
    }
}
