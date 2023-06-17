package com.gm.movienet.feature.listener

import com.gm.movienet.feature.movie.model.Movie

/**
 * Created by @godman on 16/06/23.
 */

interface OnMovieListener {
    fun onMovieSelecter(data: Movie?) {}
}