package com.example.omapp.data.network.model

import com.example.omapp.jsonresponses.detailMovieResponse
import com.google.gson.Gson
import junit.framework.Assert
import org.junit.Test

class MovieDetailResponseDTOTest{
    @Test
    fun `given response when parsing then dto is valid`() {
        val output = Gson().fromJson(detailMovieResponse, MovieDetailResponseDTO::class.java)

        Assert.assertNotNull(output)
    }
}