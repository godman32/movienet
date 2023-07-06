package com.gm.movienet.feature.genre.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 05/07/23.
 */

data class Genre (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null
)