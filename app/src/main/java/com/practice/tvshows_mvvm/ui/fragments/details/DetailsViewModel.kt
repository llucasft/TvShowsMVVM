package com.practice.tvshows_mvvm.ui.fragments.details

import androidx.lifecycle.ViewModel
import com.practice.tvshows_mvvm.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: TvShowRepository
): ViewModel() {
}