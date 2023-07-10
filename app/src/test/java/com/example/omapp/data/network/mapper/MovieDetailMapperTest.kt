package com.example.omapp.data.network.mapper

import com.example.omapp.*
import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.MovieDTO
import com.example.omapp.data.network.model.MovieDetailResponseDTO
import com.example.omapp.domain.model.Movie
import com.example.omapp.movieListDTO
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class MovieDetailMapperTest {

    private lateinit var sut: Mapper<Movie, MovieDetailResponseDTO>

    @Before
    fun setUp() {
        sut = MovieDetailMapper()
    }

    @Test
    fun `GIVEN movieDTO WHEN map THEN return correct movie`() {
        val input = movieDetailDTO
        val expected = movie

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN OTHER movieDTO WHEN map THEN return correct movie`() {
        val input = otherMovieDetailDTO
        val expected = otherMovie

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }
}