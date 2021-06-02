package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.MovieListFragAdapter
import com.android.tutorial.couch_potato.model.Movie
import com.android.tutorial.couch_potato.rest.RestClient
import com.android.tutorial.couch_potato.util.MovieDelegate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListFragment : Fragment(), MovieDelegate {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_movie_list, container, false)
        val adapter = MovieListFragAdapter(this)
        val rvMovies: RecyclerView = view.findViewById(R.id.rvLatestMovies)
        val movieList = mutableListOf<Movie>()
        setMovie("batman", movieList)
        setMovie("frozen", movieList)
        setMovie("harry-potter", movieList)
        Log.d("response", "total size............." + (movieList.size))
        adapter.setNewData(movieList)
        movieList.forEach {
            Log.d("response", "movie title.....................${it.title}")
        }
        rvMovies.adapter = adapter
        rvMovies.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    override fun onMovieDetailClicked(movie: Movie) {
        Log.d("response", "click movie.........${movie.imdbID}")
    }

    private fun setMovie(title: String, movieList: MutableList<Movie>) {
        RestClient.getApiService()
            .getByTitle(title, "full")
            .enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    response: Response<Movie>
                ) {
                    if (response.isSuccessful) {
                        Log.d("response", "......success")
                        response.body()?.let {
                            movieList.add(it)
                            Log.d("response", "size............." + (movieList.size))
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d("response", "......fail")
                }
            })
    }

}