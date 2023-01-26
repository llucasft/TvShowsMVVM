package com.practice.tvshows_mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.tvshows_mvvm.adapter.TvShowAdapter
import com.practice.tvshows_mvvm.databinding.ActivityMainBinding
import com.practice.tvshows_mvvm.models.TvShowItem
import com.practice.tvshows_mvvm.ui.TvShowDetailActivity
import com.practice.tvshows_mvvm.viewmodel.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TvShowsViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        observers()
    }

    private fun observers() {
        viewModel.responseTvShow.observe(this) { listTvShows ->
            if (listTvShows.isNotEmpty()) {
                setUpRv(listTvShows)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setUpRv(listTvShows: List<TvShowItem>) {
        tvShowAdapter = TvShowAdapter(listTvShows)

        binding.recyclerView.apply {
            adapter = tvShowAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        tvShowAdapter.setOnItemClickListener(object : TvShowAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                listTvShows[position]
                val intent = Intent(applicationContext, TvShowDetailActivity::class.java)
                intent.putExtra("id", listTvShows[position].id)
                startActivity(intent)
            }
        })
    }
}