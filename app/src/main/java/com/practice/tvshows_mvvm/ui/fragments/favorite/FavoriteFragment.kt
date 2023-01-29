package com.practice.tvshows_mvvm.ui.fragments.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.practice.tvshows_mvvm.databinding.FragmentFavoriteBinding
import com.practice.tvshows_mvvm.ui.fragments.base.BaseFragment

class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val viewModel: FavoriteViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
}