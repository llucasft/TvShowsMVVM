package com.practice.tvshows_mvvm.ui.fragments.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.practice.tvshows_mvvm.databinding.FragmentDetailsBinding
import com.practice.tvshows_mvvm.models.TvShowDetail
import com.practice.tvshows_mvvm.ui.fragments.base.BaseFragment
import com.practice.tvshows_mvvm.util.removeHtmlTags
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment: BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private var tvShowId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTvShow()
    }

    private fun loadTvShow() {
        tvShowId = args.tvShowId
        viewModel.getTvShow(tvShowId)
        observer()
    }

    private fun observer() {
        viewModel.responseTvShow.observe(viewLifecycleOwner) { tvShow ->
            tvShow?.let { setViews(tvShow) }
        }
    }

    private fun setViews(tvShow: TvShowDetail) = with(binding) {
        progressBar.visibility = View.GONE
        viewModel.responseTvShow.observe(viewLifecycleOwner) { tvShow ->
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

    private fun handleAddRemoveShowFromFavorites(tvShow: TvShowDetail) {
        handleBtnRemoveFavorite(tvShow)
        handleBtnAddFavorite(tvShow)
    }

    private fun handleBtnAddFavorite(tvShow: TvShowDetail) {
        binding.btnAddFavorite.setOnClickListener {
            try {
                viewModel.insertFavorite(tvShow)
                Toast.makeText(
                    context,
                    "${tvShow.name} is now a favorite!",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    "An error occurred. ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleBtnRemoveFavorite(tvShow: TvShowDetail) {
        binding.btnRemoveFavorite.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Remove ${tvShow.name} from favorites?")
                .setPositiveButton("Yes") { _, _ ->
                    try {
                        viewModel.delete(tvShow)
                        Toast.makeText(
                            context,
                            "${tvShow.name} removed from favorites. ",
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            context,
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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
}