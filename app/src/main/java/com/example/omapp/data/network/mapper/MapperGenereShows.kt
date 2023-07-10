package com.example.omapp.data.network.mapper

import com.example.omapp.common.Mapper
import com.example.omapp.data.network.model.GenereShowsDTO
import com.example.omapp.data.network.model.MovieImagesDTO
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.MovieImages
import com.example.omapp.domain.model.Show

class MapperGenereShows : Mapper<List<GenereShows>, List<GenereShowsDTO>> {

    companion object{
        const val MOVIE_GENERE_SHOWS_MAPPER_NAME = "MapperGenereShows"
    }

    override fun map(input: List<GenereShowsDTO>): List<GenereShows> {
       return input.map {
           GenereShows(
               id = it.id,
               shows = it.shows.map { showDTO ->
                   Show(
                       id = showDTO.id,
                       title = showDTO.title,
                       movieImages = mapMovieImage(showDTO.movieImages),
                       actors = showDTO.actors,
                       synopsis = showDTO.synopsis,
                       synopsisEpisode = showDTO.synopsisEpisode
                   )
               }
           )
       }
    }

    private fun mapMovieImage(movieImageDTO: MovieImagesDTO) = MovieImages(
        photo = movieImageDTO.photo,
        poster = movieImageDTO.poster,
        background = movieImageDTO.background,
    )

}