package com.example.omapp.data.network.mapper

import com.example.omapp.IMAGES_BASE_PATH
import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.MovieDTO
import com.example.omapp.data.network.model.MovieDetailResponseDTO
import com.example.omapp.domain.model.Movie

class MovieDetailMapper : Mapper<Movie, MovieDetailResponseDTO> {
    companion object{
        const val MOVIE_DETAIL_MAPPER_NAME = "MovieDetailtMapper"
    }

    override fun map(input: MovieDetailResponseDTO) = with(input.response){
        Movie(
            id = this.id,
            externalId = this.externalId,
            name = this.name,
            description = this.description,
            definition = this.definition,
            year = this.year,
            duration = this.duration,
            imagesURL = this.attachments.map { attachment -> "$IMAGES_BASE_PATH${attachment.value}" },
            genres = this.genreEntityList.map { genre -> genre.name },
            isFavorite = false
        )
    }

}