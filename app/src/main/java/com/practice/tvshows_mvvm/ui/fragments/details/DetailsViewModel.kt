package com.practice.tvshows_mvvm.ui.fragments.details

import android.util.Log
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
class DetailsViewModel @Inject constructor(
    private val repository: TvShowRepository
): ViewModel() {
    private val _response = MutableLiveData<TvShowDetail>()
    val responseTvShow: LiveData<TvShowDetail>
        get() = _response

    fun getTvShow(tvShowId: Int) = viewModelScope.launch {
        repository.getShowByIdFromDb(tvShowId).collect { tvshow ->
            if (tvshow != null) {
                _response.value = tvshow
            } else {
                repository.getShowById(tvShowId).let { response ->
                    if (response.isSuccessful) {
                        _response.value = response.body()
                        Log.i("TvShowViewModel", "getAllTvShows Success: ${response.body()}")
                    } else {
                        Log.i("TvShowViewModel", "getAllTvShows Error: ${response.code()}")
                    }
                }
            }
        }
    }

    fun insertFavorite(tvShow: TvShowDetail) = viewModelScope.launch {
        tvShow.favorite = true
        repository.insertFavorite(tvShow)
    }

    fun delete(tvShow: TvShowDetail) = viewModelScope.launch {
        repository.delete(tvShow)
    }
}