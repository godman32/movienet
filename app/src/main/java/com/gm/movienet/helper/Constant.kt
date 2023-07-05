package com.gm.mvies.feature.helper

/**
 * Created by @godman on 04/07/23.
 */

class ImageURL {
    companion object{
        const val W154= "https://image.tmdb.org/t/p/w154"
        const val W300= "https://image.tmdb.org/t/p/w300"
        const val W780= "https://image.tmdb.org/t/p/w780"
        const val W92= "https://image.tmdb.org/t/p/W92"
    }
}



enum class Status {
    SUCCESS,
    LOADING,
    IDLE,
    LOAD,
    FREE,
    CLEAR,
    ERROR
}

