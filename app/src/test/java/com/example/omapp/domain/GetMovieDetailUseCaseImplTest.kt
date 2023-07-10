package com.example.omapp.domain

import com.example.omapp.common.DataResponse
import com.example.omapp.genereShows
import com.example.omapp.otherGenereShows
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetGenereShowsCaseImplTest {

    private lateinit var sut: GetGenereShowsUseCase
    private val repository = mockk<Repository>()

    @Before
    fun setUp() {
        sut = GetGenereShowsUseCaseImpl(repository)
    }

    @Test
    fun `GIVEN genere shows from repository WHEN invoke THEN return Data Response`() {
        runBlocking {
            val railId = "12abd"
            val expected = listOf(genereShows)
            coEvery { repository.getGenereShow(any()) } returns DataResponse.Success(expected)

            val actual = sut.invoke(railId).first()

            coVerify { repository.getGenereShow(railId) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN OTHER genere shows from repository WHEN invoke THEN return Data Response`() {
        runBlocking {
            val railId = "other"
            val expected = listOf(otherGenereShows)
            coEvery { repository.getGenereShow(any()) } returns DataResponse.Success(expected)

            val actual = sut.invoke(railId).first()

            coVerify { repository.getGenereShow(railId) }
            assertEquals(expected, actual)
        }
    }
}