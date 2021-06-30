package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.BookmarkMovieListAdapter
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.MovieHistory
import com.android.tutorial.couch_potato.util.Constant
import com.android.tutorial.couch_potato.util.ManageMovieHistory
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.frag_history.*

class HistoryBookmarkFragment : Fragment(), MovieListener {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var adapter: BookmarkMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_history_bookmark, container, false)
        adapter = BookmarkMovieListAdapter(this)
        viewModel = MovieDetailViewModel()
        val rvMovies: RecyclerView = view.findViewById(R.id.rvBookmarkMovies)
        FirebaseFirestore.getInstance().collection("bookmark-movies").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("isBookmark") == true) {
                        viewModel.getById(document.data.getValue("imdbId").toString())
                        viewModel.movieById.observe(viewLifecycleOwner, { movie ->
                            adapter.setNewData(movie)
                        })
                    }
                }
            }
        }
        rvMovies.adapter = adapter
        rvMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return view
    }

    override fun onFavoriteClicked(movie: MovieHistory) {
    }

    override fun onBookmarkClicked(movie: MovieHistory) {
        ManageMovieHistory.manage(movie, Constant.BOOKMARK_PATH)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

}