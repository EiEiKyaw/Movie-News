package com.android.tutorial.couch_potato.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.util.MovieDelegate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieListFragAdapter(
    private val delegate: MovieDelegate
) : RecyclerView.Adapter<MovieListFragAdapter.MyViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]
        holder.itemView.apply {
            Glide.with(context)
                .load(movie.posterImg)
                .error(R.drawable.sample_poster)
                .into(ivMoviePoster)
            tvMovieTitle.text = "${movie.title} - ${movie.year}"

            setOnClickListener {
                delegate.onMovieDetailClicked(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setNewData(list: List<Movie>){
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }

}