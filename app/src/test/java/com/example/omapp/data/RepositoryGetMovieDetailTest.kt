package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.common.ErrorResponse
import com.example.omapp.data.local.LocalDataSource
import com.example.omapp.data.network.NetworkDataSource
import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.domain.Repository
import com.example.omapp.domain.model.Movie
import com.example.omapp.movie
import com.example.omapp.otherMovie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class RepositoryGetMovieDetailTest {

    private lateinit var sut: Repository
    private val networkDataSource = mockk<NetworkDataSource>(relaxed = true)
    private val localDataSource = mockk<LocalDataSource>(relaxed = true)

    @Before
    fun setUp() {
        sut = RepositoryImpl(networkDataSource, mockk(), localDataSource)
    }

    @Test
    fun `GIVEN Success response from networkData source and no Favorite WHEN getMovieList THEN return correct result`() {
        runBlocking {
            val id = "ab123"
            val networkResponse = DataResponse.Success(movie)
            val expected = DataResponse.Success(movieNoFavorite)
            coEvery { networkDataSource.getDetail(any()) } returns networkResponse
            coEvery { localDataSource.checkIfFavorite(any()) } returns false

            val actual = sut.getMovieDetail(id)

            coVerify { networkDataSource.getDetail(id) }
            coVerify { localDataSource.checkIfFavorite(movie.id) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN OTHER Success response from networkData source WHEN getMovieList THEN return result`() {
        runBlocking {
            val id = "er456"
            val networkResponse = DataResponse.Success(otherMovie)
            val expected = DataResponse.Success(movieFavorite)
            coEvery { networkDataSource.getDetail(any()) } returns networkResponse
            coEvery { localDataSource.checkIfFavorite(any()) } returns true

            val actual = sut.getMovieDetail(id)

            coVerify { networkDataSource.getDetail(id) }
            coVerify { localDataSource.checkIfFavorite(otherMovie.id) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN Failure response from networkData source WHEN getMovieList THEN return correct result`() {
        runBlocking {
            val id = "er456"
            val expected = DataResponse.Failure(ErrorResponse.Unexpected("Error message"))
            coEvery { networkDataSource.getDetail(any()) } returns expected

            val actual = sut.getMovieDetail(id)

            coVerify { networkDataSource.getDetail(id) }
            assertEquals(expected, actual)
        }
    }

    private val movieNoFavorite = Movie(
        id = 12345,
        externalId = "12abc",
        name = "movie name",
        description = "description",
        definition = "HD",
        year = 2017,
        duration = 7691000,
        imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/url"),
        genres = listOf("Drama"),
        isFavorite = false
    )

    private val movieFavorite = Movie(
        id = 786543,
        externalId = "34dfg",
        name = "Other movie name",
        description = "other description",
        definition = "HD",
        year = 2019,
        duration = 7691000,
        imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/other url"),
        genres = listOf("Action"),
        isFavorite = true
    )

}