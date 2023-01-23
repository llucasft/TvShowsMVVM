package com.practice.tvshows_mvvm.repository

import com.practice.tvshows_mvvm.api.ApiService
import com.practice.tvshows_mvvm.models.TvShowItem
import retrofit2.Response
import javax.inject.Inject

class TvShowRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTvShows() = apiService.getTvShows()

    suspend fun getShowById(tvShowId: Int) = apiService.getShowById(tvShowId)
}