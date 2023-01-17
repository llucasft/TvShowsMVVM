package com.practice.tvshows_mvvm.repository

import com.practice.tvshows_mvvm.api.ApiService
import javax.inject.Inject

class TvShowRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTvShows() = apiService.getTvShows()
}