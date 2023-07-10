package com.example.omapp.data.local.mapper

import com.example.omapp.*
import com.example.omapp.common.Mapper
import com.example.omapp.data.local.room.MovieRoomModel
import com.example.omapp.domain.model.Movie
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class MovieToLocalModelMapperTest {

    private lateinit var sut : Mapper<MovieRoomModel, Movie>

    @Before
    fun setUp() {
        sut = MovieToLocalModelMapper()
    }

    @Test
    fun `GIVEN movie WHEN map THEN return correct movie room`() {
        val input = movie
        val expected = movieRoom

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN OTHER movie WHEN map THEN return correct movie room`() {
        val input = annotherMovie
        val expected = annotherMovieRoom

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }
}