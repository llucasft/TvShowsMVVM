package com.practice.tvshows_mvvm.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.practice.tvshows_mvvm.databinding.ActivityTvShowDetailBinding
import com.practice.tvshows_mvvm.models.TvShowDetail
import com.practice.tvshows_mvvm.util.removeHtmlTags
import com.practice.tvshows_mvvm.viewmodel.TvShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowDetailBinding
    private val viewModel: TvShowViewModel by viewModels()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        loadTvShow()
    }

    private fun loadTvShow() {
        id = intent.getIntExtra("id", 0)
        viewModel.getTvShow(id)
        observer()
    }

    private fun observer() {
        viewModel.responseTvShow.observe(this) { tvShow ->
            tvShow?.let { setViews(tvShow) }
        }
    }

    private fun handleAddRemoveShowFromFavorites(tvShow: TvShowDetail) {
        handleBtnRemoveFavorite(tvShow)
        handleBtnAddFavorite(tvShow)
    }

    private fun handleBtnAddFavorite(tvShow: TvShowDetail) {
        binding.btnAddFavorite.setOnClickListener {
            try {
                viewModel.insertFavorite(tvShow)
                Toast.makeText(
                    this@TvShowDetailActivity,
                    "${tvShow.name} is now a favorite!",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@TvShowDetailActivity,
                    "An error occurred. ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleBtnRemoveFavorite(tvShow: TvShowDetail) {
        binding.btnRemoveFavorite.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Remove ${tvShow.name} from favorites?")
                .setPositiveButton("Yes") { _, _ ->
                    try {
                        viewModel.delete(tvShow)
                        Toast.makeText(
                            this@TvShowDetailActivity,
                            "${tvShow.name} removed from favorites. ",
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@TvShowDetailActivity,
                            "An error occurred. ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun setViews(tvShow: TvShowDetail) = with(binding) {
        progressBar.visibility = View.GONE
        viewModel.responseTvShow.observe(this@TvShowDetailActivity) { tvShow ->
            if (tvShow.favorite) {
                btnAddFavorite.visibility = View.GONE
                btnRemoveFavorite.visibility = View.VISIBLE
            } else {
                btnRemoveFavorite.visibility = View.GONE
                btnAddFavorite.visibility = View.VISIBLE
            }
        }
        handleAddRemoveShowFromFavorites(tvShow)
        val apiSummary = tvShow.summary
        val formattedSummary = apiSummary.removeHtmlTags()
        tvShowRatings.text = "${tvShow.rating.average}"
        tvShowGenre.text = tvShow.genres[0]
        tvShowSummary.text = formattedSummary
        tvShowImage.load(tvShow.image.original) {
            crossfade(true)
            crossfade(1000)
        }
    }
}