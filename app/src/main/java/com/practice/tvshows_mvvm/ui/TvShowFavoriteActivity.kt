package com.practice.tvshows_mvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.tvshows_mvvm.adapter.TvShowAdapter
import com.practice.tvshows_mvvm.adapter.TvShowsFavoriteAdapter
import com.practice.tvshows_mvvm.databinding.ActivityTvShowFavoriteBinding
import com.practice.tvshows_mvvm.models.TvShowDetail
import com.practice.tvshows_mvvm.viewmodel.TvShowsFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvShowFavoriteBinding
    private val viewModel: TvShowsFavoriteViewModel by viewModels()
    private lateinit var tvShowsFavoriteAdapter: TvShowsFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "My Favorites"
        observers()
    }

    private fun observers() = lifecycleScope.launch {
        viewModel.favorites.observe(this@TvShowFavoriteActivity) { favoritesList ->
            if (favoritesList.isEmpty()) {
                binding.tvEmptyList.visibility = View.VISIBLE
            }
            setupRecyclerView(favoritesList)
        }
    }

    private fun setupRecyclerView(favoritesList: List<TvShowDetail>) {
        tvShowsFavoriteAdapter = TvShowsFavoriteAdapter(favoritesList)
        binding.rvFavoriteTvShows.apply {
            adapter = tvShowsFavoriteAdapter
            layoutManager = LinearLayoutManager(this@TvShowFavoriteActivity)
        }
        ItemTouchHelper(itemTouchHelperCallBack()).attachToRecyclerView(binding.rvFavoriteTvShows)
        recyclerViewClickListener(favoritesList)
    }

    private fun recyclerViewClickListener(favoritesList: List<TvShowDetail>) {
        tvShowsFavoriteAdapter.setOnItemClickListener(object :
            TvShowsFavoriteAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(
                    this@TvShowFavoriteActivity,
                    TvShowDetailActivity::class.java
                ).apply {
                    putExtra("id", favoritesList[position].id)
                }
                startActivity(intent)
            }
        })
    }

    private fun itemTouchHelperCallBack(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val tvShow = tvShowsFavoriteAdapter.getTvShowPosition(viewHolder.adapterPosition)
                viewModel.delete(tvShow).also {
                    Toast.makeText(
                        this@TvShowFavoriteActivity,
                        "${tvShow.name} removed from favorites. ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}