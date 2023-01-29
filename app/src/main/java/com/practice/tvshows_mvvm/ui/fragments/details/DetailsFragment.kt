package com.practice.tvshows_mvvm.ui.fragments.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.practice.tvshows_mvvm.databinding.FragmentDetailsBinding
import com.practice.tvshows_mvvm.ui.fragments.base.BaseFragment

class DetailsFragment: BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
}