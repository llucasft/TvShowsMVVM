package com.practice.tvshows_mvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvShowDetail(
    val genres: List<String>,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
    val rating: Rating,
    val summary: String,
    var favorite: Boolean = false
)