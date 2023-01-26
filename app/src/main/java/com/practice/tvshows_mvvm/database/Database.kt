package com.practice.tvshows_mvvm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.practice.tvshows_mvvm.database.converters.DatabaseConverters
import com.practice.tvshows_mvvm.database.dao.TvShowsDao
import com.practice.tvshows_mvvm.models.TvShowDetail

@Database(entities = [TvShowDetail::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun dao(): TvShowsDao
}