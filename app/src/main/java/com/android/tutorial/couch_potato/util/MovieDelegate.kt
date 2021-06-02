package com.android.tutorial.couch_potato.util

import com.android.tutorial.couch_potato.model.Movie

interface MovieDelegate {
    fun onMovieDetailClicked(movie: Movie)
}