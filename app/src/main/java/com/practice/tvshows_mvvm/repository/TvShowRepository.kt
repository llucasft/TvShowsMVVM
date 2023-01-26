package com.practice.tvshows_mvvm.repository

import com.practice.tvshows_mvvm.api.ApiService
import com.practice.tvshows_mvvm.database.dao.TvShowsDao
import com.practice.tvshows_mvvm.models.TvShowDetail
import javax.inject.Inject

class TvShowRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: TvShowsDao
) {
    suspend fun getTvShows() = apiService.getTvShows()
    suspend fun getShowById(tvShowId: Int) = apiService.getShowById(tvShowId)

    suspend fun insert(tvShowDetail: TvShowDetail) = dao.insert(tvShowDetail)
    fun getAll() = dao.getAll()
    suspend fun delete(tvShowDetail: TvShowDetail) = dao.delete(tvShowDetail)
}