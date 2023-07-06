package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName


/**
 * Created by @godman on 05/07/23.
 */
data class Author (

    @SerializedName("name"        ) var name       : String? = null,
    @SerializedName("username"    ) var username   : String? = null,
    @SerializedName("avatar_path" ) var avatarPath : String? = null,
    @SerializedName("rating"      ) var rating     : Int?    = null

)