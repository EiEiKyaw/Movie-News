package com.android.tutorial.couch_potato.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.activity.MovieDetailActivity
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.model.MovieHistory
import com.android.tutorial.couch_potato.util.Constant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_detail.view.ivMoviePoster
import kotlinx.android.synthetic.main.item_movie_detail.view.tvMovieTitle
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieListFragAdapter(val listener: MovieListener) :
    BaseAdapter<Movie, MovieListFragAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = getLayoutInflator(parent).inflate(R.layout.item_movie_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = itemList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(movie.posterImg)
                .error(R.drawable.sample_poster)
                .into(ivMoviePoster)
            tvMovieTitle.text = movie.title
        }

        var isFavorite = holder.itemView.ivFavorite.isSelected
        var isBookmark = holder.itemView.ivBookmark.isSelected
        holder.itemView.ivFavorite.setOnClickListener {
            isFavorite = !isFavorite
            holder.itemView.ivFavorite.isSelected = isFavorite
            val movieHistory = MovieHistory(
                imdbId = movie.imdbId,
                isFavorite = isFavorite,
                isBookmark = isBookmark
            )
            listener.onFavoriteClicked(movieHistory)
        }

        holder.itemView.ivBookmark.setOnClickListener {
            isBookmark = !isBookmark
            holder.itemView.ivBookmark.isSelected = isBookmark
            val movieHistory = MovieHistory(
                imdbId = movie.imdbId,
                isFavorite = isFavorite,
                isBookmark = isBookmark
            )
            listener.onBookmarkClicked(movieHistory)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java)

            intent.putExtra(Constant.MOVIE_POSTER, movie.posterImg)
            intent.putExtra(Constant.MOVIE_TITLE, movie.title)
            intent.putExtra(Constant.RELEASED_DATE, movie.releasedYear)
            intent.putExtra(Constant.DESCRIPTION, movie.plot)
            intent.putExtra(Constant.IMDB_RATING, movie.imdbRating)
            intent.putExtra(Constant.IMDB_VOTES, movie.imdbVotes)
            intent.putExtra(Constant.AWARDS, movie.awards)
            intent.putExtra(Constant.ACTORS, movie.actors)
            intent.putExtra(Constant.GENRE, movie.category)
            intent.putExtra(Constant.RUNTIME, movie.runtime)
            intent.putExtra(Constant.IMDB_ID, movie.imdbId)
            intent.putExtra(Constant.IS_FAVORITE, holder.itemView.ivFavorite.isSelected)
            intent.putExtra(Constant.IS_BOOKMARK, holder.itemView.ivBookmark.isSelected)
            context.startActivity(intent)
        }
    }

}