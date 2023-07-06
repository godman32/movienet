package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 05/07/23.
 */

data class Reviews (

    @SerializedName("id"            ) var id           : Int?               = null,
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var reviews      : ArrayList<Review> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null

)