package com.example.omapp.data.network.mapper

import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.GenereShowsDTO
import com.example.omapp.domain.model.GenereShows
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class MapperGenereShowsTest {

    private lateinit var sut: Mapper<List<GenereShows>, List<GenereShowsDTO>>

    @Before
    fun setUp() {
        sut = MapperGenereShows()
    }

    @Test
    fun `Given DTO List when invoked, then return domain list`() {
        val expected = listOf(
            GenereShows(
                id = "ab12",
                shows = emptyList()
            )
        )
        val input = listOf(
                GenereShowsDTO(
                id = "ab12",
                shows = emptyList()
            )
        )

        val actual = sut.map(input)


        assertEquals(expected, actual)
    }

    @Test
    fun `Given OTHER DTO List when invoked, then return domain list`() {
        val expected = listOf(
                GenereShows(
                    id = "cd45",
                    shows = emptyList()
                )
        )
        val input =  listOf(
            GenereShowsDTO(
                id = "cd45",
                shows = emptyList()
            )
        )

        val actual = sut.map(input)


        assertEquals(expected, actual)
    }
}