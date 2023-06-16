package com.gm.movienet.feature.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gm.movienet.R
import com.gm.movienet.databinding.FragmentGenresBinding
import com.gm.movienet.feature.listener.OnGenreListener

class GenresFragment(
    private val genres:List<Genre>,
    private val onGenreListener: OnGenreListener) : DialogFragment(), OnGenreListener {

    private val binding by lazy { FragmentGenresBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.rvGenres.apply {
            adapter = GenresAdapter(genres, this@GenresFragment)
        }

        return binding.root
    }


    override fun onGenreSelected(data: Genre?) {
        super.onGenreSelected(data)

        onGenreListener.onGenreSelected(data)
        dismissAllowingStateLoss()
    }

    override fun getTheme(): Int {
        return R.style.AppTheme_FullScreenDialog
    }
}