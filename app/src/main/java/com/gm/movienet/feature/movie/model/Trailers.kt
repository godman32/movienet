package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 16/06/23.
 */

data class Trailers (

    @SerializedName("id"      ) var id      : Int?               = null,
    @SerializedName("results" ) var trailers : ArrayList<Trailer> = arrayListOf()

)