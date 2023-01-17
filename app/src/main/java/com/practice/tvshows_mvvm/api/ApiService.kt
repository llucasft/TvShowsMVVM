package com.practice.tvshows_mvvm.api

import com.practice.tvshows_mvvm.models.TvShowResponse
import com.practice.tvshows_mvvm.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getTvShows(): Response<TvShowResponse>
}