package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.data.local.LocalDataSource
import com.example.omapp.data.local.room.MovieFavoriteRoomModel
import com.example.omapp.domain.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class RepositorySetFavoriteTest {

    private lateinit var sut: Repository
    private val localDataSource = mockk<LocalDataSource>()

    @Before
    fun setUp() {
        sut = RepositoryImpl(mockk(), mockk(), localDataSource)
    }

    @Test
    fun `GIVEN movie id WHEN set favorite true THEN value is passed to LocalDataSource`() {
        runBlocking {
            val id = 1234L
            val isFavorite = true
            val expected = DataResponse.Success(isFavorite)
            coEvery { localDataSource.setFavoriteMovie(any(), any()) } returns expected

            val actual = sut.setFavorite(id = id, isFavorite = isFavorite)

            coVerify { localDataSource.setFavoriteMovie(id, isFavorite) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN movie id WHEN set favorite false THEN value is passed to LocalDataSource`() {
        runBlocking {
            val id = 1234L
            val isFavorite = false
            val expected = DataResponse.Success(isFavorite)
            coEvery { localDataSource.setFavoriteMovie(any(), any()) } returns expected

            val actual = sut.setFavorite(id = id, isFavorite = isFavorite)

            coVerify { localDataSource.setFavoriteMovie(id, isFavorite) }
            assertEquals(expected, actual)
        }
    }
}