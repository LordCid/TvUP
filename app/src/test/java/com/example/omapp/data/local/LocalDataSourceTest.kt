package com.example.omapp.data.local

import com.example.omapp.ERROR_DATABASE_GENERIC_MESSAGE
import com.example.omapp.common.DataResponse
import com.example.omapp.common.ErrorResponse
import com.example.omapp.data.local.mapper.LocalModelToMovieMapper
import com.example.omapp.data.local.mapper.MovieToLocalModelMapper
import com.example.omapp.data.local.room.MovieDao
import com.example.omapp.data.local.room.MovieFavoriteRoomModel
import com.example.omapp.movie
import com.example.omapp.movieRoom
import io.mockk.*
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test

class LocalDataSourceTest {

    private lateinit var sut: LocalDataSource
    private val movieDao = mockk<MovieDao>(relaxed = true)
    private val movieToLocalMapper = MovieToLocalModelMapper()
    private val localToMovieMapper = LocalModelToMovieMapper()

    @Before
    fun setUp() {
        sut = LocalDataSourceImpl(movieDao, movieToLocalMapper, localToMovieMapper)
    }

    @Test
    fun `Given list of movies WHEN store list THEN  list is stored in cache`() {
        runBlocking {
            val movies = listOf(movie)
            val localModelList = listOf(movieRoom)
            coEvery { movieDao.insertMovies(any()) } returns emptyList()

            sut.storeMovies(movies)

            coVerify { movieDao.insertMovies(localModelList) }
        }
    }

    @Test
    fun `GIVEN movies in cache WHEN get movies THEN return movies`() {
        runBlocking {
            val movies = listOf(movie)
            val localModelList = listOf(movieRoom)
            val expected = DataResponse.Success(movies)
            coEvery { movieDao.getMovies() } returns localModelList

            val actual = sut.getMovieList()

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN no movies in cache WHEN get movies THEN return movies`() {
        runBlocking {
            val expected = DataResponse.Failure(ErrorResponse.Unexpected(ERROR_DATABASE_GENERIC_MESSAGE))
            coEvery { movieDao.getMovies() } returns emptyList()

            val actual = sut.getMovieList()

            actual as DataResponse.Failure
            assertEquals(expected.error.message, actual.error.message)
        }
    }

    @Test
    fun `GIVEN movies in cache WHEN invalidate THEN cache is deleted`() {
        runBlocking {

            sut.invalidate()

            coVerify { movieDao.deleteMovies() }
        }
    }

    @Test
    fun `GIVEN movie id and persitance succeds WHEN set favorite true THEN value is persisted and returns true`() {
        runBlocking {
            val id = 1234L
            val movieRoomFavorite = MovieFavoriteRoomModel(id)
            coEvery { movieDao.insertFavorite(any()) } returns 1L
            val expected = DataResponse.Success(true)

            val actual = sut.setFavoriteMovie(id = id, isFavorite = true)

            coVerify { movieDao.insertFavorite(movieRoomFavorite) }
            assertEquals(expected, actual)
        }
    }


    @Test
    fun `GIVEN movie id and persitance succeds WHEN set favorite false THEN value is persisted and returns isFavorite false`() {
        runBlocking {
            val id = 1234L
            val movieRoomFavorite = MovieFavoriteRoomModel(id)
            coEvery { movieDao.deleteFavorite(any()) } returns 1
            val expected = DataResponse.Success(false)

            val actual = sut.setFavoriteMovie(id = id, isFavorite = false)

            coVerify { movieDao.deleteFavorite(movieRoomFavorite) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN movie id and persitance fails WHEN set favorite false THEN value is persisted and returns isFavorite false`() {
        runBlocking {
            val id = 1234L
            coEvery { movieDao.insertFavorite(any()) } returns 0L
            val expected = DataResponse.Failure(ErrorResponse.Unexpected(ERROR_DATABASE_GENERIC_MESSAGE))

            val actual = sut.setFavoriteMovie(id = id, isFavorite = false)

            actual as DataResponse.Failure
            assertEquals(expected.error.message, actual.error.message)
        }
    }

    @Test
    fun `GIVEN movie id and favorite persited WHEN check favorite THEN succed is return with favorite true`() {
        runBlocking {
            val id = 1234L
            val movieRoomFavorite = MovieFavoriteRoomModel(id)
            coEvery { movieDao.checkIfFavorite(any()) } returns movieRoomFavorite
            val expected = true

            val actual = sut.checkIfFavorite(id = id)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN movie id and no favorite persited WHEN check favorite THEN succed is return with favorite false`() {
        runBlocking {
            val id = 1234L
            coEvery { movieDao.checkIfFavorite(any()) } returns null
            val expected = false

            val actual = sut.checkIfFavorite(id = id)

            assertEquals(expected, actual)
        }
    }

}