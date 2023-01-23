package com.practice.tvshows_mvvm.api

import com.practice.tvshows_mvvm.models.TvShowItem
import com.practice.tvshows_mvvm.models.TvShowResponse
import com.practice.tvshows_mvvm.util.Constants.END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(END_POINT)
    suspend fun getTvShows(): Response<TvShowResponse>

    @GET("shows/{showId}")
    suspend fun getShowById(@Path("showId") showId: Int): Response<TvShowItem>
}