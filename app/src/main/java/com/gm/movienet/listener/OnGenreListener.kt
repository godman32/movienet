package com.gm.movienet.feature.listener

import com.gm.movienet.feature.genre.Genre


/**
 * Created by @godman on 16/06/23.
 */

interface OnGenreListener {
    fun onGenreSelected(data: Genre?) {}
}