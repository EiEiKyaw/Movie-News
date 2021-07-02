package com.android.tutorial.couch_potato.fragment

import androidx.fragment.app.Fragment
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.MovieHistory
import com.android.tutorial.couch_potato.util.Constant
import com.android.tutorial.couch_potato.util.ManageMovieHistory

open class BaseFragment : Fragment(), MovieListener {
    override fun onFavoriteClicked(movie: MovieHistory) {
        ManageMovieHistory.manage(movie, Constant.FAVORITE_PATH)
    }

    override fun onBookmarkClicked(movie: MovieHistory) {
        ManageMovieHistory.manage(movie, Constant.BOOKMARK_PATH)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}