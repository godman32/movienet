package com.gm.movienet.feature.movie.detail

import android.app.Application
import androidx.lifecycle.*
import com.gm.movienet.R
import com.gm.movienet.Utills.Event
import com.gm.movienet.Utills.Resource
import com.gm.movienet.Utills.Utils
import com.gm.movienet.app.MyApplication
import com.gm.movienet.conn.AppRepository
import com.gm.movienet.feature.movie.model.Movie
import com.gm.movienet.feature.movie.model.Reviews
import com.gm.movienet.feature.movie.model.Trailers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


/**
 * Created by @godman on 04/07/23.
 */

class DetailMovieVM(app: Application, private val repository: AppRepository) : AndroidViewModel(app) {


    private val _movie = MutableLiveData<Event<Resource<Movie>>>()
    val movie: LiveData<Event<Resource<Movie>>> = _movie

    private val _trailer = MutableLiveData<Event<Resource<Trailers>>>()
    val trailer: LiveData<Event<Resource<Trailers>>> = _trailer

    private val _reviews = MutableLiveData<Event<Resource<Reviews>>>()
    val reviews: LiveData<Event<Resource<Reviews>>> = _reviews

    var page= 1
    var movieID= 0


    fun getDetail() = viewModelScope.launch(Dispatchers.IO) {
        loadDetailMovie()
    }

    fun getTrailers() = viewModelScope.launch(Dispatchers.IO) {
        loadTrailer()
    }

    fun getReviews() = viewModelScope.launch(Dispatchers.IO) {
        loadReviews()
    }


    private suspend fun loadDetailMovie() {
        _movie.postValue(Event(Resource.Loading()))
        delay(100L)
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.detailMovie(movieID)
                _movie.postValue(handleMoviesResponse(response))
            } else {
                _movie.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _movie.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _movie.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }

    private suspend fun loadTrailer() {
        _trailer.postValue(Event(Resource.Loading()))
        delay(100L)
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getTrailer(movieID, 1)
                _trailer.postValue(handleTrailerResponse(response))
            } else {
                _trailer.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _trailer.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _trailer.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }

    private suspend fun loadReviews() {
        _reviews.postValue(Event(Resource.Loading()))
        delay(100L)
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getReviews(movieID, page)
                _reviews.postValue(handleReviewsResponse(response))
            } else {
                _reviews.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _reviews.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _reviews.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }


    private fun handleMoviesResponse(response: Response<Movie>): Event<Resource<Movie>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    private fun handleTrailerResponse(response: Response<Trailers>): Event<Resource<Trailers>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    private fun handleReviewsResponse(response: Response<Reviews>): Event<Resource<Reviews>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}