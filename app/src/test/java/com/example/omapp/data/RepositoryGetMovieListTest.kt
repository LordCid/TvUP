package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.data.local.LocalDataSource
import com.example.omapp.data.network.NetworkDataSource
import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.domain.Repository
import com.example.omapp.domain.model.Movie
import com.example.omapp.movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test
import java.util.*

class RepositoryGetMovieListTest {

    private lateinit var sut: Repository
    private val networkDataSource = mockk<NetworkDataSource>(relaxed = true)
    private val localDataSource = mockk<LocalDataSource>(relaxed = true)

    @Before
    fun setUp() {
        sut = RepositoryImpl(networkDataSource, mockk(), localDataSource)
    }

    @Test
    fun `GIVEN no cache and success response from networkdatasource WHEN getmovies THEN get from network and store results`() {
        runBlocking {
            val page = 1
            val movies = listOf(movie)
            givenNoCacheAndMoviesFromNetwork(movies)

            sut.getMovieList(page)

            coVerify { networkDataSource.getMovieList(page) }
            coVerify { localDataSource.storeMovies(movies) }
        }
    }


    @Test
    fun `GIVEN no cache success response from networkdatasource WHEN getmovies THEN get from network and return results`() {
        runBlocking {
            val page = 1
            val movies = listOf(movie)
            givenNoCacheAndMoviesFromNetwork(movies)

            val actual = sut.getMovieList(page)

            coVerify { networkDataSource.getMovieList(page) }
            assertEquals(DataResponse.Success(movies), actual)
        }
    }

    @Test
    fun `GIVEN valid cache WHEN get movies THEN cache is returned`() {
        runBlocking {
            val page = 2
            val movies = listOf(movie)
            givenPreviouslyStoredCache(movies)
            givenInstantWhenValidCache()

            val actual = sut.getMovieList(page)

            coVerify(exactly = 0) { networkDataSource.getMovieList(page) }
            coVerify(exactly = 1) { localDataSource.getMovieList() }
            assertEquals(DataResponse.Success(movies), actual)
        }
    }

    @Test
    fun `GIVEN invalid cache and network success WHEN get movies THEN network movies are returned and cache is replaced`() {
        runBlocking {
            val page = 2
            val movies = listOf(movie)
            givenPreviouslyStoredCache(movies)
            givenInstantWhenInvalidCache()

            val actual = sut.getMovieList(page)

            coVerify(exactly = 1) { networkDataSource.getMovieList(page) }
            coVerify(atLeast = 2) { localDataSource.storeMovies(movies) }
            coVerify(exactly = 0){ localDataSource.getMovieList() }
            coVerify(exactly = 1) { localDataSource.invalidate() }
            assertEquals(DataResponse.Success(movies), actual)
        }
    }

    @Test
    fun `GIVEN local cache valid WHEN get movie list THEN check movie favorite status`() {
        runBlocking {
            val page = 2
            val id = 12345L
            val movies = listOf(movie)
            givenPreviouslyStoredCache(movies)
            givenInstantWhenValidCache()

            val actual = sut.getMovieList(page)

            coVerify { localDataSource.checkIfFavorite(id) }
            assertEquals(DataResponse.Success(movies), actual)
        }
    }

    @Test
    fun `GIVEN local cache invalid and Network success response WHEN get movie list THEN check movie favorite status`() {
        runBlocking {
            val page = 2
            val id = 12345L
            val movies = listOf(movie)
            givenNoCacheAndMoviesFromNetwork(movies)

            val actual = sut.getMovieList(page)

            coVerify { localDataSource.checkIfFavorite(id) }
            assertEquals(DataResponse.Success(movies), actual)
        }
    }

    private fun givenNoCacheAndMoviesFromNetwork(movies: List<Movie>) {
        coEvery { networkDataSource.getMovieList(any()) } returns DataResponse.Success(movies)
        coEvery { localDataSource.getMovieList() } returns DataResponse.Success(emptyList())
        coEvery { localDataSource.storeMovies(any()) } returns Unit
    }


    private suspend fun givenPreviouslyStoredCache(movies: List<Movie>){
        sut.currentDate = {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, 2018)
                set(Calendar.MONTH, 2)
                set(Calendar.DAY_OF_MONTH, 16)
                set(Calendar.HOUR, 10)
                set(Calendar.MINUTE, 10)
                set(Calendar.SECOND, 0)
            }.time
        }
        givenNoCacheAndMoviesFromNetwork(movies)
        sut.getMovieList(1)
        coEvery { localDataSource.getMovieList() } returns DataResponse.Success(movies)
    }

    private fun givenInstantWhenValidCache() {
        sut.currentDate = {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, 2018)
                set(Calendar.MONTH, 2)
                set(Calendar.DAY_OF_MONTH, 16)
                set(Calendar.HOUR, 10)
                set(Calendar.MINUTE, 10)
                set(Calendar.SECOND, 15)
            }.time
        }
    }

    private fun givenInstantWhenInvalidCache() {
        sut.currentDate = {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, 2018)
                set(Calendar.MONTH, 2)
                set(Calendar.DAY_OF_MONTH, 16)
                set(Calendar.HOUR, 10)
                set(Calendar.MINUTE, 35)
            }.time
        }
    }
}