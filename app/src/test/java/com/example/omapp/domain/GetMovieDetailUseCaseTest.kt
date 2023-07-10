package com.example.omapp.domain


import com.example.omapp.common.DataResponse
import com.example.omapp.movie
import com.example.omapp.otherMovie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class GetMovieDetailUseCaseTest {

    private lateinit var sut: GetMovieDetailUseCase
    private val repository = mockk<Repository>()

    @Before
    fun setUp() {
        sut = GetMovieDetailUseCaseImpl(repository)
    }

    @Test
    fun `GIVEN movies from repository WHEN invoke THEN return Data Response`() {
        runBlocking {
            val id = "12abd"
            val expected = DataResponse.Success(movie)
            coEvery { repository.getMovieDetail(any()) } returns expected

            val actual = sut.invoke(id).first()

            coVerify { repository.getMovieDetail(id) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN OTHER movies from repository WHEN invoke THEN return Data Response`() {
        runBlocking {
            val id = "er4567"
            val expected = DataResponse.Success(otherMovie)
            coEvery { repository.getMovieDetail(any()) } returns expected

            val actual = sut.invoke(id).first()

            coVerify { repository.getMovieDetail(id) }
            assertEquals(expected, actual)
        }
    }
}