package com.gm.movienet.conn

import com.gm.movienet.conn.NetworkBuilder

/**
 * Created by @godman on 05/07/23.
 */

class AppRepository() {

    suspend fun getGenres() = NetworkBuilder.api.getGenres()

    suspend fun getPopularMovies(page:Int) = NetworkBuilder.api.getPopularMovie(page )

    suspend fun getMoviesGenre(page:Int, genreID:Int) = NetworkBuilder.api.getMoviesByGenre(page, genreID)

    suspend fun detailMovie(movieID:Int) = NetworkBuilder.api.detailMovie(movieID)

    suspend fun getReviews(movieID:Int, page:Int) = NetworkBuilder.api.getReviews(movieID, page)

    suspend fun getTrailer(movieID:Int, page:Int) = NetworkBuilder.api.getTrailer(movieID)
}