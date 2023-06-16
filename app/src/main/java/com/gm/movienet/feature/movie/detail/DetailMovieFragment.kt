package com.gm.movienet.feature.movie.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gm.movienet.HomeVM
import com.gm.movienet.R
import com.gm.movienet.Utills.Resource
import com.gm.movienet.ViewModelFactory
import com.gm.movienet.app.MyApplication
import com.gm.movienet.conn.MainRepository
import com.gm.movienet.databinding.FragmentDetailMovieBinding
import com.gm.movienet.feature.Movie
import com.gm.movienet.feature.listener.OnGenreListener
import com.gm.mvies.feature.helper.Status
import com.gm.mvies.feature.helper.setHidden
import com.gm.mvies.feature.helper.setVisible
import com.google.android.material.appbar.AppBarLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailMovieFragment(
    private val application: MyApplication,
    private var movie: Movie,
    private val onGenreListener: OnGenreListener) : DialogFragment() {

    private val binding by lazy { FragmentDetailMovieBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(application, MainRepository())).get(
            DetailMovieVM::class.java)}

    private val reviewsAdapter by lazy { ReviewsAdapter( ) }

    var status= Status.FREE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        onClick()
        onScroll()
        cekLayout()

        viewModel.movieID= movie.id!!
        viewModel.getDetail()
        viewModel.getTrailers()
        viewModel.getReviews()

        observe()
    }


    private fun init(){
        binding.title.text= movie.title
        binding.desc.text= movie.overview
        binding.movieReleaseDate.text= movie.releaseDate
        binding.movieRating.rating = (movie.popularity?.toFloat() ?: 0f) /2

        Glide.with(this).load("https://image.tmdb.org/t/p/w154"+movie.posterPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    binding.image.scaleType= ImageView.ScaleType.CENTER_INSIDE
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                             dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }

            })
            .error(R.mipmap.logo_black)
            .into(binding.image)
    }

    private fun onClick(){
        binding.back.setOnClickListener { dismissAllowingStateLoss() }
    }

    private fun observe(){
        viewModel.movie.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { response ->
                            movie= response
                            var url= "";

                            if(movie.backdropPath.toString().equals("null")){
                                url= "https://image.tmdb.org/t/p/w780"+movie.posterPath
                            } else{
                                url= "https://image.tmdb.org/t/p/w780"+movie.backdropPath
                            }

                            Glide.with(this).load(url)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                                              isFirstResource: Boolean): Boolean {
                                        binding.movieBackdrop.scaleType= ImageView.ScaleType.CENTER_INSIDE
                                        return false
                                    }
                                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                        return false
                                    }

                                })
                                .error(R.mipmap.logo_red)
                                .into(binding.movieBackdrop)
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            binding.movieBackdrop.setImageResource(R.mipmap.logo_red)
                            binding.movieBackdrop.scaleType= ImageView.ScaleType.CENTER_INSIDE
//                            progress.errorSnack(message, Snackbar.LENGTH_LONG)
                        }
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        })

        viewModel.trailer.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { response ->
                            if(response.trailers.size > 0){
                                binding.lTrailer.setVisible()
                                lifecycle.addObserver(binding.trailer)
                                binding.trailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        // loading the selected video
                                        // into the YouTube Player
                                        youTubePlayer.cueVideo(response.trailers[0].key.toString(), 0f)
                                    }

                                    override fun onStateChange(
                                        youTubePlayer: YouTubePlayer,
                                        state: PlayerConstants.PlayerState
                                    ) {
                                        super.onStateChange(youTubePlayer, state)
                                    }
                                })
                            }
                        }

                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
//                            progress.errorSnack(message, Snackbar.LENGTH_LONG)
                        }
                    }

                    is Resource.Loading -> {
                        binding.load.setVisible()
                    }
                }
            }
        })

        viewModel.reviews.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { response ->
                            binding.load.setHidden()
                            if(response.reviews.size > 0){
                                binding.lReviews.setVisible()
                                reviewsAdapter.add(response.reviews)
                                if(binding.rvReviews.adapter == null){
                                    binding.rvReviews.apply {
                                        adapter = reviewsAdapter
                                    }
                                }
                                binding.rvReviews.post(Runnable { reviewsAdapter.notifyItemInserted(reviewsAdapter.itemCount)
                                    cekLayout()})
                            } else{
                                status= Status.CLEAR
                            }
                        }

                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
//                            progress.errorSnack(message, Snackbar.LENGTH_LONG)
                        }
                    }

                    is Resource.Loading -> {
                        binding.load.setVisible()
                    }
                }
            }
        })
    }

    private fun cekLayout(){
        val params = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        if((binding.scroll.getChildAt(0).getBottom() - binding.scroll.getHeight()) < 500){
            behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return false
                }
            })
        } else{
            behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return true
                }
            })
        }
    }

    private fun onScroll(){
        binding.scroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val scrollViewHeight: Int = binding.scroll.getChildAt(0).getBottom() - binding.scroll.getHeight()

            if(!binding.load.isVisible && scrollY > scrollViewHeight- 2000 && status== Status.FREE){
                viewModel.page++
                viewModel.getReviews()
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.AppTheme_FullScreenDialogSolid
    }
}