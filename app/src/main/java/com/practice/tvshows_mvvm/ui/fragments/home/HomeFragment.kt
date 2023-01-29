package com.practice.tvshows_mvvm.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.tvshows_mvvm.adapter.TvShowAdapter
import com.practice.tvshows_mvvm.databinding.FragmentHomeBinding
import com.practice.tvshows_mvvm.models.TvShowItem
import com.practice.tvshows_mvvm.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
//        setupFavoritesButton()
    }

    private fun observers() {
        viewModel.responseTvShow.observe(viewLifecycleOwner) { listTvShows ->
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
            layoutManager = LinearLayoutManager(context)
        }

        tvShowAdapter.setOnItemClickListener(object : TvShowAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailsFragment(listTvShows[position].id)
                findNavController().navigate(action)
            }
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}