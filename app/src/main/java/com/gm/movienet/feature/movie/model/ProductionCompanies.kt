package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName


/**
 * Created by @godman on 05/07/23.
 */

data class ProductionCompanies (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("logo_path"      ) var logoPath      : String? = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("origin_country" ) var originCountry : String? = null

)