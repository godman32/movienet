package com.gm.movienet

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gm.movienet.conn.AppRepository
import com.gm.movienet.feature.movie.detail.DetailMovieVM

/**
 * Created by @godman on 04/07/23.
 */

class ViewModelFactory(
    val app: Application,
    val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeVM::class.java)) {
            return HomeVM(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(DetailMovieVM::class.java)) {
            return DetailMovieVM(app, appRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}