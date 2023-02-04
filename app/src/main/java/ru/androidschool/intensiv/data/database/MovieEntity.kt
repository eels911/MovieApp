package ru.androidschool.intensiv.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?
){

}