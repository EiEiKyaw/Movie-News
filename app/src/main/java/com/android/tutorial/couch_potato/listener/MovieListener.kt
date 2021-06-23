package com.android.tutorial.couch_potato.listener

import com.android.tutorial.couch_potato.model.Movie

interface MovieListener {
    fun onMovieDetailClicked(movie: Movie)
}