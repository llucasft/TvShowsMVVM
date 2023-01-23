package com.practice.tvshows_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.tvshows_mvvm.models.TvShowItem
import com.practice.tvshows_mvvm.repository.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val repository: TvShowRepository
) : ViewModel() {
    private val _response =  MutableLiveData<List<TvShowItem>>()
    val responseTvShow: LiveData<List<TvShowItem>>
        get() = _response

    init {
        getAllTvShows()
    }

    private fun getAllTvShows() = viewModelScope.launch {
        repository.getTvShows().let { response ->
            if (response.isSuccessful){
                _response.value = response.body()
                Log.i("TvShowViewModel", "getAllTvShows Success: ${response.body()}")
            } else {
                Log.i("TvShowViewModel", "getAllTvShows Error: ${response.code()}")
            }
        }
    }
}