package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.MovieListFragAdapter
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.MovieHistory
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
        viewModel.movieByTitle.observe(viewLifecycleOwner, Observer { movie ->
            adapter.setNewData(movie)
        })
    }

    override fun onFavoriteClicked(movie: MovieHistory) {
        manageMovieHistory(movie, "favorite-movies")
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


    private fun manageMovieHistory(movie: MovieHistory, path: String) {
        val collection = FirebaseFirestore.getInstance().collection(path)
        val data: MutableMap<String, Any> = HashMap()
        data["imdbId"] = movie.imdbId
        if (path.contains("favorite"))
            data["isFavorite"] = movie.isFavorite
        else if (path.contains("bookmark"))
            data["isBookmark"] = movie.isBookmark
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
                    if (path.contains("favorite"))
                        collection.document(documentId).update("isFavorite", movie.isFavorite)
                    else if (path.contains("bookmark"))
                        collection.document(documentId).update("isBookmark", movie.isBookmark)
                }
            }
        }
    }
}
