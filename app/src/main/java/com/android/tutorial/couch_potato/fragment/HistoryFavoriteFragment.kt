package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.FavoriteMovieListAdapter
import com.android.tutorial.couch_potato.model.MovieHistory
import com.android.tutorial.couch_potato.util.Constant
import com.android.tutorial.couch_potato.util.ManageMovieHistory
import com.android.tutorial.couch_potato.viewmodel.MovieDetailViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.frag_history.*

class HistoryFavoriteFragment : BaseFragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var adapter: FavoriteMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_history_favorite, container, false)
        adapter = FavoriteMovieListAdapter(this)
        viewModel = MovieDetailViewModel()
        val rvMovies: RecyclerView = view.findViewById(R.id.rvFavoriteMovies)
        FirebaseFirestore.getInstance().collection("favorite-movies").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    if (document.data.getValue("isFavorite") == true) {
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
        ManageMovieHistory.manage(movie, Constant.FAVORITE_PATH)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}