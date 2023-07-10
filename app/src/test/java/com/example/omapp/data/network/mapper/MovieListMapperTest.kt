package com.example.omapp.data.network.mapper

import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.MovieListResponseDTO
import com.example.omapp.domain.model.Movie
import com.example.omapp.movie
import com.example.omapp.movieListDTO
import com.example.omapp.otherMovie
import com.example.omapp.otherMovieListDTO
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class MovieListMapperTest {

    private lateinit var sut: Mapper<List<Movie>, MovieListResponseDTO>

    @Before
    fun setUp() {
        sut = MovieListMapper()
    }

    @Test
    fun `GIVEN movieListDTO WHEN map THEN return correct movie list`() {
        val input = movieListDTO
        val expected = listOf(movie)

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN OTHER movieListDTO WHEN map THEN return correct movie list`() {
        val input = otherMovieListDTO
        val expected = listOf(otherMovie)

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }
}