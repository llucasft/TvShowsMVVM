package com.practice.tvshows_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.practice.tvshows_mvvm.databinding.ActivityTvShowDetailBinding
import com.practice.tvshows_mvvm.viewmodel.TvShowViewModel

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowDetailBinding
    private val viewModel: TvShowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTvShow()
    }

    private fun loadTvShow() {
        val id = intent.getIntExtra("id", 0)
        viewModel.getTvShow(id)
    }
}