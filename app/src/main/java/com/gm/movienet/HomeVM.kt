package com.gm.movienet

import android.app.Application
import androidx.lifecycle.*
import com.gm.movienet.Utills.Event
import com.gm.movienet.Utills.Resource
import com.gm.movienet.Utills.Utils
import com.gm.movienet.app.MyApplication
import com.gm.movienet.conn.AppRepository
import com.gm.movienet.feature.movie.model.Movies
import com.gm.movienet.feature.genre.model.Genres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

/**
 * Created by @godman on 16/06/23.
 */

class HomeVM(app: Application, private val repository: AppRepository) : AndroidViewModel(app) {

    private val _genres = MutableLiveData<Event<Resource<Genres>>>()
    val genres: LiveData<Event<Resource<Genres>>> = _genres


    private val _movies = MutableLiveData<Event<Resource<Movies>>>()
    val movies: LiveData<Event<Resource<Movies>>> = _movies

    var page= 1
    var genreID= 0


    fun getGenres() = viewModelScope.launch(Dispatchers.IO) {
        loadGenres()
    }

    fun getPopular() = viewModelScope.launch(Dispatchers.IO) {
        loadPopular()
    }

    fun getMoviesGenre() = viewModelScope.launch(Dispatchers.IO) {
        loadMoviesGenre()
    }

    private suspend fun loadGenres() {
        _genres.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getGenres()
                _genres.postValue(handleGenresResponse(response))
            } else {
                _genres.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _genres.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _genres.postValue(
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

    private suspend fun loadPopular() {
        _movies.postValue(Event(Resource.Loading()))
        delay(100L)
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getPopularMovies(page)
                _movies.postValue(handleMoviesResponse(response))
            } else {
                _movies.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _movies.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _movies.postValue(
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

    private suspend fun loadMoviesGenre() {
        _movies.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getMoviesGenre(page, genreID)
                _movies.postValue(handleMoviesResponse(response))
            } else {
                _movies.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _movies.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _movies.postValue(
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

    private fun handleGenresResponse(response: Response<Genres>): Event<Resource<Genres>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    private fun handleMoviesResponse(response: Response<Movies>): Event<Resource<Movies>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}