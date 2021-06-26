package com.android.tutorial.couch_potato.listener

import com.android.tutorial.couch_potato.model.MovieHistory

interface MovieListener {
    fun onFavoriteClicked(movie: MovieHistory)
    fun onBookmarkClicked(movie: MovieHistory)
}