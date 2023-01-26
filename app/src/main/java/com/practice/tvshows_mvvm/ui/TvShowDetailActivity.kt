package com.practice.tvshows_mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.practice.tvshows_mvvm.databinding.ActivityTvShowDetailBinding
import com.practice.tvshows_mvvm.util.removeHtmlTags
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
        viewModel.responseTvShow.observe(this@TvShowDetailActivity) { tvShow ->
            tvShow?.let {
                val apiSummary = tvShow.summary
                val formattedSummary = apiSummary.removeHtmlTags()
                progressBar.visibility = View.GONE
                imageStarRating.visibility = View.VISIBLE
                tvShowImage.visibility = View.VISIBLE
                tvShowSummary.visibility = View.VISIBLE
//                flexBoxLayout.visibility = View.VISIBLE
                tvShowGenre.visibility = View.VISIBLE
                tvShowRatings.visibility = View.VISIBLE
                tvShowRatings.text = "${tvShow.rating.average}"
                tvShowGenre.text = tvShow.genres[0]
                tvShowSummary.text = formattedSummary
//                setupGenresRecyclerView(tvShow.genres)
                tvShowImage.load(tvShow.image.original) {
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

//    private fun setupGenresRecyclerView(genres: List<String>) = with(binding) {
//        val genreAdapter = GenreAdapter(genres)
//        tvShowGenresRecyclerView.adapter = genreAdapter
//    }
}