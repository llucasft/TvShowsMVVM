package com.practice.tvshows_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import coil.load
import com.practice.tvshows_mvvm.databinding.ActivityTvShowDetailBinding
import com.practice.tvshows_mvvm.viewmodel.TvShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowDetailBinding
    private val viewModel: TvShowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        loadTvShow()
    }

    private fun loadTvShow() {
        val id = intent.getIntExtra("id", 0)
        viewModel.getTvShow(id)
        observer()
    }

    private fun observer() = with(binding) {
        viewModel.responseTvShow.observe(this@TvShowDetailActivity) {tvShow ->
            tvShow?.let {
                progressBar.visibility = View.GONE
                tvShowName.visibility = View.VISIBLE
                tvShowImage.visibility = View.VISIBLE
                tvShowName.text = tvShow.name
                tvShowImage.load(tvShow.image.original) {
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }
}