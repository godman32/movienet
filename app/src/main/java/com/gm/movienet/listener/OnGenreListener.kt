package com.gm.movienet.feature.listener

import com.gm.movienet.feature.genre.model.Genre

/**
 * Created by @godman on 05/07/23.
 */

interface OnGenreListener {
    fun onGenreSelected(data: Genre?) {}
}