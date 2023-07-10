package com.example.omapp.data.local.mapper

import com.example.omapp.annotherMovie
import com.example.omapp.annotherMovieRoom
import com.example.omapp.common.Mapper
import com.example.omapp.data.local.room.MovieRoomModel
import com.example.omapp.domain.model.Movie
import com.example.omapp.movie
import com.example.omapp.movieRoom
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class LocalModelToMovieMapperTest {

    private lateinit var sut: Mapper<Movie, MovieRoomModel>

    @Before
    fun setUp() {
        sut = LocalModelToMovieMapper()
    }

    @Test
    fun `GIVEN movie room WHEN map THEN return correct movie `() {
        val input = movieRoom
        val expected = movie

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN OTHER movie room WHEN map THEN return correct movie`() {
        val input = annotherMovieRoom
        val expected = annotherMovie

        val actual = sut.map(input)

        assertEquals(expected, actual)
    }
}