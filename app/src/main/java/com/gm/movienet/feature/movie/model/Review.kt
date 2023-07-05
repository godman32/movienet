package com.gm.movienet.feature.movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 04/07/23.
 */

data class Review (

    @SerializedName("author"         ) var author        : String?        = null,
    @SerializedName("author_details" ) var authorDetails : Author? = Author(),
    @SerializedName("content"        ) var content       : String?        = null,
    @SerializedName("created_at"     ) var createdAt     : String?        = null,
    @SerializedName("id"             ) var id            : String?        = null,
    @SerializedName("updated_at"     ) var updatedAt     : String?        = null,
    @SerializedName("url"            ) var url           : String?        = null

)