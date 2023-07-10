package com.example.omapp.data

import com.example.omapp.data.network.model.MovieListResponseDTO
import com.example.omapp.jsonresponses.listMovieResponse
import com.google.gson.Gson
import junit.framework.Assert.assertNotNull
import org.junit.Test

class MovieListResponseDTOTest {
    @Test
    fun `given response when parsing then dto is valid`() {
        val output = Gson().fromJson(listMovieResponse, MovieListResponseDTO::class.java)

        assertNotNull(output)
    }
}