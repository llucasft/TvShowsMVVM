package com.practice.tvshows_mvvm.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.practice.tvshows_mvvm.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TvShowRepository
): ViewModel() {
}