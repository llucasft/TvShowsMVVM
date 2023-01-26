package com.practice.tvshows_mvvm.database.dao

import androidx.room.*
import com.practice.tvshows_mvvm.models.TvShowDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShow: TvShowDetail) : Long

    @Query("SELECT * FROM tvShowDetail ORDER BY id")
    fun getAll(): Flow<List<TvShowDetail>>

    @Delete
    suspend fun delete(tvShow: TvShowDetail)
}