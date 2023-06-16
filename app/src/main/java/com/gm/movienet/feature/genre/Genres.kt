package com.gm.movienet.feature.genre


import com.google.gson.annotations.SerializedName


data class Genres (
    @SerializedName("genres" ) var genres : ArrayList<Genre> = arrayListOf()
)