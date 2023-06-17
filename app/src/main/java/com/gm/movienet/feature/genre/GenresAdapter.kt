package com.gm.movienet.feature.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm.movienet.databinding.ItemGenreBinding
import com.gm.movienet.feature.genre.model.Genre
import com.gm.movienet.feature.listener.OnGenreListener

/**
 * Created by @godman on 16/06/23.
 */

class GenresAdapter(private val genres: List<Genre>, private val onGenreListener: OnGenreListener) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.genre.text = genres[position].name

        holder.binding.genre.setOnClickListener {
            onGenreListener.onGenreSelected(genres[position])
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}