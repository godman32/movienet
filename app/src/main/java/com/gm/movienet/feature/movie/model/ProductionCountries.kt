package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 05/07/23.
 */

data class ProductionCountries (

    @SerializedName("iso_3166_1" ) var iso31661 : String? = null,
    @SerializedName("name"       ) var name     : String? = null

)