package com.gm.movienet.conn

import com.gm.movienet.BuildConfig
import com.gm.movienet.feature.movie.model.Movie
import com.gm.movienet.feature.movie.model.Movies
import com.gm.movienet.feature.movie.model.Reviews
import com.gm.movienet.feature.movie.model.Trailers
import com.gm.movienet.feature.genre.model.Genres
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by @godman on 05/07/23.
 */

interface ApiServices {


    @GET("genre/movie/list?language=en")
    suspend fun getGenres() : Response<Genres>
//
    @GET("movie/popular?language=en-US&api_key="+ BuildConfig.Api_Token)
    suspend fun getPopularMovie(@Query("page") page: Int) : Response<Movies>

    @GET("discover/movie?language=en&sort_by=release_date.desc&api_key="+BuildConfig.Api_Token)
    suspend fun getMoviesByGenre(@Query("page") page: Int, @Query("with_genres") genres: Int) : Response<Movies>

    @GET("movie/{id}?language=en-US&api_key="+BuildConfig.Api_Token)
    suspend fun detailMovie(@Path("id") id: Int): Response<Movie>

    @GET("movie/{id}/videos?api_key="+BuildConfig.Api_Token)
    suspend fun getTrailer(@Path("id") id: Int) : Response<Trailers>

    @GET("movie/{id}}/reviews?language=en-US")
    suspend fun getReviews(@Path("id") id: Int, @Query("page") page: Int) : Response<Reviews>

}