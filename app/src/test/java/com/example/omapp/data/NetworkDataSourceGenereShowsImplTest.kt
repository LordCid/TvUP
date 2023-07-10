package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.data.network.ApiService
import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.data.network.NetworkDataSourceGenereShowsImpl
import com.example.omapp.data.network.mapper.MapperGenereShows
import com.example.omapp.genereShows
import com.example.omapp.genereShowsDTO
import com.example.omapp.otherGenereShows
import com.example.omapp.otherGenereShowsDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.mock.Calls

class NetworkDataSourceGenereShowsImplTest {

    private lateinit var sut: NetworkDataSourceGenereShows
    private val service = mockk<ApiService>()
    private val mapper = MapperGenereShows()

    @Before
    fun setUp() {
        sut = NetworkDataSourceGenereShowsImpl(service, mapper)
    }

    @Test
    fun `GIVEN response success with genere show list WHEN get genereShows THEN return correct data response success`() {
        runBlocking {
            val railId = "id"
            val expected = DataResponse.Success(listOf(genereShows))
            coEvery { service.getGeneresShows(any()) } returns Calls.response(listOf(genereShowsDTO))

            val actual = sut.getGenereShows(railId)

            coVerify { service.getGeneresShows(railId) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN OTHER response success with genere show list WHEN get genereShows THEN return correct data response success`() {
        runBlocking {
            val railId = "id"
            val expected = DataResponse.Success(listOf(otherGenereShows))
            coEvery { service.getGeneresShows(any()) } returns Calls.response(listOf(otherGenereShowsDTO))

            val actual = sut.getGenereShows(railId)

            coVerify { service.getGeneresShows(railId) }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `GIVEN response failure WHEN get genere show THEN return correct data response success`() {
        runBlocking {
            val railId = "id"
            coEvery { service.getGeneresShows(any()) } returns Calls.failure(mockk())

            val actual = sut.getGenereShows(railId)

            coVerify { service.getGeneresShows(railId) }
            assertTrue(actual.isFailure)
        }
    }

}