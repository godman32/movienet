package com.gm.movienet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gm.movienet.Utills.Resource
import com.gm.movienet.app.MyApplication
import com.gm.movienet.conn.AppRepository
import com.gm.movienet.databinding.ActivityHomeBinding
import com.gm.movienet.feature.movie.model.Movie
import com.gm.movienet.feature.genre.model.Genre
import com.gm.movienet.feature.genre.GenresFragment
import com.gm.movienet.feature.listener.OnGenreListener
import com.gm.movienet.feature.listener.OnMovieListener
import com.gm.movienet.feature.movie.MoviesAdapter
import com.gm.movienet.feature.movie.detail.DetailMovieFragment
import com.gm.mvies.feature.helper.Status
import com.gm.mvies.feature.helper.setHidden
import com.gm.mvies.feature.helper.setVisible
import com.gm.mvies.feature.listener.OnLoadDataListener
import com.gm.mvies.feature.listener.OnScrollFullListener

/**
 * Created by @godman on 05/07/23.
 */
class HomeActivity : AppCompatActivity(), OnGenreListener, OnScrollFullListener, OnMovieListener,
    OnLoadDataListener {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel by lazy {ViewModelProvider(this, ViewModelFactory(application, AppRepository())).get(HomeVM::class.java)}

    private val moviesAdapter by lazy { MoviesAdapter( this, this) }

    private var genres:List<Genre> = arrayListOf()

    private var status= Status.FREE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getGenres()
        viewModel.getPopular()

        onClick()
        ObserverData()
    }


    fun onClick(){
        binding.category.setOnClickListener {
            GenresFragment(genres, this@HomeActivity
            ).show(this.supportFragmentManager, "G")
        }

        binding.reset.setOnClickListener {
            binding.lPopular.visibility= View.VISIBLE
            binding.lGenre.visibility= View.GONE
            binding.reset.visibility= View.GONE
            moviesAdapter.clearData()

            viewModel.page= 1
            viewModel.getPopular()
        }
    }

    override fun onGenreSelected(data: Genre?) {
        super.onGenreSelected(data)

        binding.lPopular.visibility= View.GONE
        binding.lGenre.visibility= View.VISIBLE
        binding.reset.visibility= View.VISIBLE

        binding.genre.text= data?.name.toString()
        binding.lGenre.visibility= View.VISIBLE

        moviesAdapter.clearData()
        viewModel.page= 1
        viewModel.genreID= data?.id!!.toInt()
        viewModel.getMoviesGenre()
    }

    override fun onScrollFull() {
        super.onScrollFull()
        viewModel.page++
        status= Status.LOAD

        if(binding.reset.visibility == View.VISIBLE){
            viewModel.getMoviesGenre()
        } else
            viewModel.getPopular()

    }

    override fun onMovieSelecter(data: Movie?) {
        super.onMovieSelecter(data)

        DetailMovieFragment(application as MyApplication, data!!, this)
            .show(this.supportFragmentManager, "M")
    }

    private fun ObserverData(){
        viewModel.genres.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { response ->
                            genres= response.genres
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let {}
                    }

                    is Resource.Loading -> {}
                }
            }
        })


        viewModel.movies.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        binding.load.setHidden()
                        response.data?.let { response ->
//                            binding.load.setHidden()
                            if(response.movies.size> 0){
                                status= Status.FREE
                                moviesAdapter.add(response.movies)
                                if(binding.rvMovies.adapter == null){
                                    binding.rvMovies.apply {
                                        layoutManager = GridLayoutManager(this@HomeActivity,2)
                                        adapter = moviesAdapter
                                    }
                                }
                                binding.rvMovies.post(Runnable { moviesAdapter.notifyItemInserted(moviesAdapter.itemCount)})
                            } else
                                status= Status.CLEAR
                        }
                    }
                    is Resource.Error -> {
                        binding.load.setHidden()
                        response.message?.let { }
                    }

                    is Resource.Loading -> {
                        binding.load.setVisible()
                    }
                }
            }
        })
    }
}