package com.practice.tvshows_mvvm.viewmodel

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
class TvShowViewModel @Inject constructor(
    private val repository: TvShowRepository
) : ViewModel() {
    private val _response =  MutableLiveData<TvShowDetail>()
    val responseTvShow: LiveData<TvShowDetail>
        get() = _response

    fun getTvShow(tvShowId: Int) = viewModelScope.launch {
        repository.getShowById(tvShowId).let { response ->
            if (response.isSuccessful){
                _response.value = response.body()
                Log.i("TvShowViewModel", "getAllTvShows Success: ${response.body()}")
            } else {
                Log.i("TvShowViewModel", "getAllTvShows Error: ${response.code()}")
            }
        }
    }

    fun insert(tvShow: TvShowDetail) = viewModelScope.launch {
        repository.insert(tvShow)
    }
}