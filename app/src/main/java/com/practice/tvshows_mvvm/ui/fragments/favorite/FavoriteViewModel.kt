package com.practice.tvshows_mvvm.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.tvshows_mvvm.models.TvShowDetail
import com.practice.tvshows_mvvm.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: TvShowRepository
): ViewModel() {
    private val _favorites = MutableLiveData<List<TvShowDetail>>()
    val favorites: LiveData<List<TvShowDetail>> = _favorites

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        repository.getAll().collect { results ->
            if (results.isEmpty()) {
                _favorites.value = emptyList()
            } else {
                _favorites.value = results
            }
        }
    }

    fun delete(tvShow: TvShowDetail) = viewModelScope.launch {
        repository.delete(tvShow)
    }
}