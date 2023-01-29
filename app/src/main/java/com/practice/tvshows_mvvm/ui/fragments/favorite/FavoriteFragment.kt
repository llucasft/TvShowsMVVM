package com.practice.tvshows_mvvm.ui.fragments.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.tvshows_mvvm.adapter.TvShowsFavoriteAdapter
import com.practice.tvshows_mvvm.databinding.FragmentFavoriteBinding
import com.practice.tvshows_mvvm.models.TvShowDetail
import com.practice.tvshows_mvvm.ui.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var tvShowsFavoriteAdapter: TvShowsFavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }

    private fun observers() = lifecycleScope.launch {
        viewModel.favorites.observe(viewLifecycleOwner) { favoritesList ->
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
            layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(itemTouchHelperCallBack()).attachToRecyclerView(binding.rvFavoriteTvShows)
        recyclerViewClickListener(favoritesList)
    }

    private fun recyclerViewClickListener(favoritesList: List<TvShowDetail>) {
        tvShowsFavoriteAdapter.setOnItemClickListener(object :
            TvShowsFavoriteAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val action = FavoriteFragmentDirections
                    .actionFavoriteFragmentToDetailsFragment(favoritesList[position].id)
                findNavController().navigate(action)
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
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Remove ${tvShow.name} from favorites?")
                    .setPositiveButton("Yes") { _, _ ->
                        try {
                            viewModel.delete(tvShow)
                            tvShowsFavoriteAdapter.notifyItemRemoved(viewHolder.adapterPosition)
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
                        tvShowsFavoriteAdapter.notifyItemChanged(viewHolder.adapterPosition)
                        dialog.cancel()
                    }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
}