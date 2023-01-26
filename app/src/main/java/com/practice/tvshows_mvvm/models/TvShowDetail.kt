package com.practice.tvshows_mvvm.models

data class TvShowDetail(
    val genres: List<String>,
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
    val rating: Rating,
    val summary: String,
)