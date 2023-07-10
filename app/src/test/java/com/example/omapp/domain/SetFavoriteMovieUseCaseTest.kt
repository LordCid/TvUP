package com.example.omapp.domain

import com.example.omapp.common.DataResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class SetFavoriteMovieUseCaseTest {

    private lateinit var sut : SetFavoriteMovieUseCase
    private val repository = mockk<Repository>()

    @Before
    fun setUp() {
        sut = SetFavoriteMovieUseCaseImpl(repository)
    }

    @Test
    fun `GIVEN repository set Favorite succed WHEN invoked THEN true is returned`() {
        runBlocking {
            val id = 1234L
            val isFavorite = true
            val expected = DataResponse.Success(isFavorite)
            coEvery { repository.setFavorite(any(), any()) } returns expected

            val actual = sut.invoke(id, isFavorite)

            coVerify { repository.setFavorite(id, isFavorite) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN repository set Favorite fails WHEN invoked THEN false is returned`() {
        runBlocking {
            val id = 4564L
            val isFavorite = false
            val expected = DataResponse.Success(isFavorite)
            coEvery { repository.setFavorite(any(), any()) } returns expected

            val actual = sut.invoke(id, isFavorite)

            coVerify { repository.setFavorite(id, isFavorite) }
            assertEquals(expected, actual)
        }
    }
}