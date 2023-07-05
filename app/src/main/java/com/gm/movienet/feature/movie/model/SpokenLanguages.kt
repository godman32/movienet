package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 04/07/23.
 */
data class SpokenLanguages (
    @SerializedName("english_name" ) var englishName : String? = null,
    @SerializedName("iso_639_1"    ) var iso6391     : String? = null,
    @SerializedName("name"         ) var name        : String? = null
)