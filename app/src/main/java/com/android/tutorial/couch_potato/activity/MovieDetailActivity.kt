package com.android.tutorial.couch_potato.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.util.Constant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_detail.*


class MovieDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_movie_detail)

        val moviePoster = intent.getStringExtra(Constant.MOVIE_POSTER) ?: ""
        val movieTitle = intent.getStringExtra(Constant.MOVIE_TITLE) ?: ""
        val releasedDate = intent.getStringExtra(Constant.RELEASED_DATE) ?: ""
        val description = intent.getStringExtra(Constant.DESCRIPTION) ?: ""
        val imdbRating = intent.getStringExtra(Constant.IMDB_RATING) ?: ""
        val imdbVotes = intent.getStringExtra(Constant.IMDB_VOTES) ?: ""
        val awards = intent.getStringExtra(Constant.AWARDS) ?: ""
        val actors = intent.getStringExtra(Constant.ACTORS) ?: ""
        val genre = intent.getStringExtra(Constant.GENRE) ?: ""
        val runtime = intent.getStringExtra(Constant.RUNTIME) ?: ""

        Glide.with(this)
            .load(moviePoster)
            .error(R.drawable.sample_poster)
            .into(ivMoviePoster)
        tvMovieTitle.text = movieTitle
        tvReleasedDate.text = releasedDate
        tvRating.text = imdbRating
        tvVotes.text = "( $imdbVotes votes )"
        tvDescription.text = description
        tvViewLink.text = moviePoster
        tvAwards.text = awards
        tvActors.text = actors
        tvGenre.text = genre
        tvRuntime.text = runtime

        tvViewLink.setOnClickListener {
            val uri = Uri.parse(moviePoster) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}