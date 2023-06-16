package com.gm.movienet.feature.movie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gm.movienet.R
import com.gm.movienet.databinding.ItemMovieBinding
import com.gm.movienet.feature.Movie
import com.gm.movienet.feature.listener.OnMovieListener
import com.gm.mvies.feature.helper.setHidden
import com.gm.mvies.feature.helper.setVisible
import com.gm.mvies.feature.listener.OnScrollFullListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by @godman on 13/06/23.
 */

class MoviesAdapter(
    private val onScrollFullListener: OnScrollFullListener,
    private val onMovieListener: OnMovieListener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private var movies = ArrayList<Movie>()

    private var load= false
    var clear= false

    fun add(movies: List<Movie>) {
        this.movies.addAll(movies)
        load= false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        movies.clear()
        notifyDataSetChanged()
        load= false
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

            holder.binding.image.load("https://image.tmdb.org/t/p/w300"+ movies[position].posterPath){
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


        if(position > itemCount - 8 && !load && itemCount > 0){
            load= true
            onScrollFullListener.onScrollFull()
        }

        holder.binding.image.setOnClickListener { onMovieListener.onMovieSelecter(movies[position]) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}