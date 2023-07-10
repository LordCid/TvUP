package com.example.omapp.data.local.mapper

import com.example.omapp.common.Mapper
import com.example.omapp.data.local.room.MovieRoomModel
import com.example.omapp.domain.model.Movie

class LocalModelToMovieMapper: Mapper<Movie, MovieRoomModel> {
    companion object{
        const val LOCAL_TO_MOVIE_MAPPER_NAME = "LocalMovieMapper"
    }
    override fun map(input: MovieRoomModel) = Movie(
        id = input.id,
        externalId = input.externalId,
        name = input.name,
        description = input.description,
        definition = input.definition,
        year = input.year,
        duration = input.duration,
        imagesURL = input.imagesURL,
        genres = input.genres
    )
}