package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.data.network.mapper.MovieListMapper
import com.example.omapp.data.network.ApiService
import com.example.omapp.data.network.NetworkDataSource
import com.example.omapp.data.network.NetworkDataSourceImpl
import com.example.omapp.data.network.mapper.MovieDetailMapper
import com.example.omapp.movie
import com.example.omapp.movieListDTO
import com.example.omapp.otherMovie
import com.example.omapp.otherMovieListDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.mock.Calls

class NetworkDataSourceGetMovieListTest {

    private lateinit var sut : NetworkDataSource
    private val service = mockk<ApiService>()
    private val mapperList = MovieListMapper()
    private val mapperDetail = MovieDetailMapper()

    @Before
    fun setUp() {
        sut = NetworkDataSourceImpl(service, mapperList, mapperDetail)
    }

    @Test
    fun `GIVEN response success with movie list WHEN get movies page 0 THEN return correct data response success`() {
        runBlocking {
            val page = 0
            val from = 0
            val expected = DataResponse.Success(listOf(movie))
            coEvery { service.getMovies(any()) } returns Calls.response(movieListDTO)

            val actual = sut.getMovieList(page)

            coVerify { service.getMovies(from) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN response success with OTHER movie list WHEN get movies page 1 THEN return correct data response success`() {
        runBlocking {
            val page = 1
            val from = 5
            val expected = DataResponse.Success(listOf(otherMovie))
            coEvery { service.getMovies(any()) } returns Calls.response(otherMovieListDTO)

            val actual = sut.getMovieList(page)

            coVerify { service.getMovies(from) }
            assertEquals(expected, actual)
        }
    }


    @Test
    fun `GIVEN response failure WHEN get movies THEN return correct data response success`() {
        runBlocking {
            val index = 0
            coEvery { service.getMovies(any()) } returns Calls.failure(mockk())

            val actual = sut.getMovieList(index)

            coVerify { service.getMovies(index) }
            assertTrue(actual.isFailure)
        }
    }

}