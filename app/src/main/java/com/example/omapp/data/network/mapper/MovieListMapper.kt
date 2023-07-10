package com.example.omapp.data.network.mapper

import com.example.omapp.IMAGES_BASE_PATH
import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.MovieListResponseDTO
import com.example.omapp.domain.model.Movie

class MovieListMapper : Mapper<List<Movie>, MovieListResponseDTO> {
    companion object{
        const val MOVIE_LIST_MAPPER_NAME = "MovieListMapper"
    }

    override fun map(input: MovieListResponseDTO) = input.response.map {
        Movie(
            id = it.id,
            externalId = it.externalId,
            name = it.name,
            description = it.description,
            definition = it.definition,
            year = it.year,
            duration = it.duration,
            imagesURL = it.attachments.map { attachment -> "$IMAGES_BASE_PATH${attachment.value}" },
            genres = it.genreEntityList.map { genre -> genre.name },
            isFavorite = false
        )
    }
}