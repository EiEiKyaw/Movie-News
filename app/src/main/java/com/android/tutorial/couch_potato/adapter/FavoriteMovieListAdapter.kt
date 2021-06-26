package com.android.tutorial.couch_potato.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.activity.MovieDetailActivity
import com.android.tutorial.couch_potato.listener.MovieListener
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.model.MovieHistory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_fav_movie.view.*
import kotlinx.android.synthetic.main.item_movie_detail.view.ivMoviePoster
import kotlinx.android.synthetic.main.item_movie_detail.view.tvMovieTitle
import kotlinx.android.synthetic.main.item_movie_list.view.*
import kotlinx.android.synthetic.main.item_movie_list.view.ivFavorite

class FavoriteMovieListAdapter(val listener: MovieListener) :
    RecyclerView.Adapter<FavoriteMovieListAdapter.MyViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_movie, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(movie.posterImg)
                .error(R.drawable.sample_poster)
                .into(ivMoviePoster)
            tvMovieTitle.text = movie.title
        }

        holder.itemView.ivFavorite.isSelected = true
        var isFavorite = true
        holder.itemView.ivFavorite.setOnClickListener {
            isFavorite = !isFavorite
            holder.itemView.ivFavorite.isSelected = isFavorite
            val movieHistory = MovieHistory(
                imdbId = movie.imdbID,
                isFavorite = isFavorite,
                isBookmark = false
            )
            listener.onFavoriteClicked(movieHistory)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java)

            intent.putExtra("moviePoster", movie.posterImg)
            intent.putExtra("movieTitle", movie.title)
            intent.putExtra("releasedDate", movie.releasedYear)
            intent.putExtra("description", movie.plot)
            intent.putExtra("imdbRating", movie.imdbRating)
            intent.putExtra("imdbVotes", movie.imdbVotes)
            intent.putExtra("awards", movie.awards)
            intent.putExtra("actors", movie.actors)
            intent.putExtra("genre", movie.category)
            intent.putExtra("runtime", movie.runtime)

            intent.putExtra("imdbId", movie.imdbID)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setNewDataList(list: List<Movie>) {
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }

    fun setNewData(movie: Movie) {
        if (!movieList.contains(movie))
            movieList.add(movie)
        notifyItemInserted(movieList.size - 1)
    }

}