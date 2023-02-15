package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movie: MovieEntity):Completable
    @Query("SELECT * FROM Movies")
    fun getMovies(): Single<List<MovieEntity>>
    @Query("SELECT EXISTS (SELECT 1 FROM Movies WHERE movieId = :movieId)")
    fun getMovieDetails(movieId: Int): Single<Boolean>
    @Query("DELETE FROM Movies WHERE movieId = :movieId")
    fun deleteById(movieId: Int): Completable
}