package com.practice.tvshows_mvvm.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.practice.tvshows_mvvm.models.Image
import com.practice.tvshows_mvvm.models.Rating

class DatabaseConverters {

    @TypeConverter
    fun fromImage(image: Image): String = Gson().toJson(image)

    @TypeConverter
    fun toImage(image: String): Image = Gson().fromJson(image, Image::class.java)

    @TypeConverter
    fun toGenre(genre: String): List<String> {
        return genre.split(",").map { it }
    }

    @TypeConverter
    fun fromGenre(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toRating(value: String): Rating {
        val parts = value.split(",")
        return Rating(parts[0].toDouble())
    }

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return "${rating.average}"
    }
}