package com.practice.tvshows_mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.tvshows_mvvm.adapter.TvShowAdapter
import com.practice.tvshows_mvvm.databinding.ActivityMainBinding
import com.practice.tvshows_mvvm.models.TvShowItem
import com.practice.tvshows_mvvm.ui.TvShowDetailActivity
import com.practice.tvshows_mvvm.ui.TvShowFavoriteActivity
import com.practice.tvshows_mvvm.viewmodel.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.ui.setupWithNavController

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TvShowsViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Find the best TV shows"
        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener {  }
        }
    }
}