package com.example.omapp.data.local.room

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movies")
    suspend fun getMovies(): List<MovieRoomModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieRoomModel>) : List<Long>

    @Query("DELETE FROM Movies")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(movieFavorite: MovieFavoriteRoomModel) : Long

    @Delete
    suspend fun deleteFavorite(movieFavorite: MovieFavoriteRoomModel) : Int

    @Query("SELECT * FROM Favorites WHERE id = :id")
    suspend fun checkIfFavorite(id: Long): MovieFavoriteRoomModel?
}