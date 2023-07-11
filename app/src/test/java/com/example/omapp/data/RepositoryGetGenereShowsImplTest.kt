package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.common.ErrorResponse
import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.domain.Repository
import com.example.omapp.genereShows
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class RepositoryGetGenereShowsImplTest {

    private lateinit var sut: Repository
    private val networkDataSourceGenereShows = mockk<NetworkDataSourceGenereShows>(relaxed = true)

    @Before
    fun setUp() {
        sut = RepositoryImpl(networkDataSourceGenereShows)
    }

    @Test
    fun `Given success response, when get genere shows, then return result`() {
        runBlocking {
            val railId = "id"
            val expected = DataResponse.Success(listOf(genereShows))
            coEvery {
                networkDataSourceGenereShows.getGenereShows(railId)
            } returns DataResponse.Success(listOf(genereShows))

            val actual = sut.getGenereShow(railId)
            coVerify { networkDataSourceGenereShows.getGenereShows(railId) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given failure response, when get genere shows, then return error`() {
        runBlocking {
            val railId = "id"
            val errorMessage = "error"
            val expected = DataResponse.Failure(ErrorResponse.Unexpected(errorMessage))
            coEvery {
                networkDataSourceGenereShows.getGenereShows(railId)
            } returns expected

            val actual = sut.getGenereShow(railId)

            coVerify { networkDataSourceGenereShows.getGenereShows(railId) }
            assert(actual is DataResponse.Failure)
            actual as DataResponse.Failure
            assertEquals(expected.error.message, actual.error.message)
        }
    }
}