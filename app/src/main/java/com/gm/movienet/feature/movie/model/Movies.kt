package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 04/07/23.
 */

data class Movies (
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var movies      : ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null
)