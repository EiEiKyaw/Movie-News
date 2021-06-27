package com.android.tutorial.couch_potato.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.tutorial.couch_potato.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_movie_detail)

        val moviePoster = intent.getStringExtra("moviePoster") ?: ""
        val movieTitle = intent.getStringExtra("movieTitle") ?: ""
        val releasedDate = intent.getStringExtra("releasedDate") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val imdbRating = intent.getStringExtra("imdbRating") ?: ""
        val imdbVotes = intent.getStringExtra("imdbVotes") ?: ""
        val awards = intent.getStringExtra("awards") ?: ""
        val actors = intent.getStringExtra("actors") ?: ""
        val genre = intent.getStringExtra("genre") ?: ""
        val runtime = intent.getStringExtra("runtime") ?: ""

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