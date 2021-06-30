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
import com.android.tutorial.couch_potato.util.Constant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_detail.view.ivMoviePoster
import kotlinx.android.synthetic.main.item_movie_detail.view.tvMovieTitle
import kotlinx.android.synthetic.main.item_movie_list.view.*

class BookmarkMovieListAdapter(val listener: MovieListener) :
    RecyclerView.Adapter<BookmarkMovieListAdapter.MyViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bm_movie, parent, false)
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

        holder.itemView.ivBookmark.isSelected = true
        var isBookmark = true
        holder.itemView.ivBookmark.setOnClickListener {
            isBookmark = !isBookmark
            holder.itemView.ivBookmark.isSelected = isBookmark
            val movieHistory = MovieHistory(
                imdbId = movie.imdbId,
                isFavorite = false,
                isBookmark = isBookmark
            )
            listener.onBookmarkClicked(movieHistory)
            if(!isBookmark)
                removeSelected(movie)
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

    fun addSelected(movie: Movie) {
        movieList.add(movie)
        val index = movieList.indexOf(movie)
        notifyItemChanged(index)
    }

    fun removeSelected(movie: Movie) {
        movieList.remove(movie)
        val index = movieList.indexOf(movie)
        notifyItemChanged(index)
    }

    fun removeSelected(movie: MovieHistory) {
        var list = movieList.filter {
            it.imdbId == movie.imdbId
        }
        val index = movieList.indexOf(list[0])
        notifyItemChanged(index)
    }

}