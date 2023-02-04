package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface MovieDao {
    @Insert
    fun save(movie: List<MovieEntity>):Completable
    @Delete
    fun delete(movie: MovieEntity):Completable
    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<MovieEntity>>
}