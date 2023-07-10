package com.example.omapp.data.network

import com.example.omapp.*
import com.example.omapp.common.DataResponse
import com.example.omapp.data.network.mapper.MovieDetailMapper
import com.example.omapp.data.network.mapper.MovieListMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.mock.Calls

class NetworkDataSourceGetDetailTest {

    private lateinit var sut : NetworkDataSource
    private val service = mockk<ApiService>()
    private val mapperList = MovieListMapper()
    private val mapperDetail = MovieDetailMapper()

    @Before
    fun setUp() {
        sut = NetworkDataSourceImpl(service, mapperList, mapperDetail)
    }
    @Test
    fun `GIVEN response success with movie detail WHEN get movie detail THEN return correct data response success`() {
        runBlocking {
            val id = "12ab"
            val expected = DataResponse.Success(movie)
            coEvery { service.getMovieDetail(any()) } returns Calls.response(movieDetailDTO)

            val actual = sut.getDetail(id)

            coVerify { service.getMovieDetail(id) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN response success with OTHER movie detail WHEN get movie detail THEN return correct data response success`() {
        runBlocking {
            val id = "12ab"
            val expected = DataResponse.Success(otherMovie)
            coEvery { service.getMovieDetail(any()) } returns Calls.response(otherMovieDetailDTO)

            val actual = sut.getDetail(id)

            coVerify { service.getMovieDetail(id) }
            assertEquals(expected, actual)
        }
    }


    @Test
    fun `GIVEN response failure WHEN get movies THEN return correct data response success`() {
        runBlocking {
            val id = "12ab"
            coEvery { service.getMovieDetail(any()) } returns Calls.failure(mockk())

            val actual = sut.getDetail(id)

            coVerify { service.getMovieDetail(id) }
            assertTrue(actual.isFailure)
        }
    }
}