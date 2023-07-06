package com.gm.movienet.feature.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gm.movienet.R
import com.gm.movienet.databinding.ItemMovieBinding
import com.gm.movienet.feature.movie.model.Movie
import com.gm.movienet.feature.listener.OnMovieListener
import com.gm.mvies.feature.helper.ImageURL
import com.gm.mvies.feature.helper.Status
import com.gm.mvies.feature.helper.setHidden
import com.gm.mvies.feature.listener.OnScrollFullListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by @godman on 05/07/23.
 */

class MoviesAdapter(
    private val onScrollFullListener: OnScrollFullListener,
    private val onMovieListener: OnMovieListener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private var movies = ArrayList<Movie>()

    var status= Status.FREE

    fun add(movies: List<Movie>) {
        this.movies.addAll(movies)
        status= Status.FREE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        movies.clear()
        notifyDataSetChanged()
        status= Status.FREE
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text= movies[position].title
        holder.binding.movieRating.rating = (movies[position].popularity!!.toFloat()) /2


        CoroutineScope(Dispatchers.IO).launch {

            holder.binding.image.load(ImageURL.W300+ movies[position].posterPath){
                listener(
                    onSuccess = { _, _ ->
                        holder.binding.load.setHidden()
                        holder.binding.image.scaleType= ImageView.ScaleType.CENTER_CROP
                    }, onError = { _, _ ->
                        holder.binding.image.setImageResource(R.mipmap.logo_red)
                        holder.binding.image.scaleType= ImageView.ScaleType.FIT_CENTER
                        holder.binding.load.setHidden()
                    }
                )
            }
        }


        if(position > itemCount - 8 && status == Status.FREE && itemCount > 0){
            status= Status.LOAD
            onScrollFullListener.onScrollFull()
        }

        holder.binding.image.setOnClickListener { onMovieListener.onMovieSelecter(movies[position]) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}