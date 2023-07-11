package com.example.omapp.data.network.mapper

import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.GenereShowsDTO
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.genereShows
import com.example.omapp.genereShowsDTO
import com.example.omapp.otherGenereShows
import com.example.omapp.otherGenereShowsDTO
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
        val expected = listOf(genereShows)
        val input = listOf(genereShowsDTO)

        val actual = sut.map(input)


        assertEquals(expected, actual)
    }

    @Test
    fun `Given OTHER DTO List when invoked, then return domain list`() {
        val expected = listOf(otherGenereShows)
        val input =  listOf(otherGenereShowsDTO)

        val actual = sut.map(input)


        assertEquals(expected, actual)
    }
}