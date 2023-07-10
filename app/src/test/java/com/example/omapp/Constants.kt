package com.example.omapp

import com.example.omapp.data.local.room.MovieRoomModel
import com.example.omapp.data.network.model.*
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Movie
import com.example.omapp.domain.model.MovieImages
import com.example.omapp.domain.model.Show

//
//const val ERROR_DATABASE_GENERIC_MESSAGE = "Error DataBase"
//const val ERROR_NETWORK_GENERIC_MESSAGE = "Error Network"
//const val ERROR_GENERIC_MESSAGE = "Error"

val movieDTO =  MovieDTO(
    id = 12345,
    externalId = "12abc",
    name = "movie name",
    description = "description",
    definition = "HD",
    year = 2017,
    duration = 7691000,
    attachments = listOf(
        AttachmentsDTO("/url")
    ),
    genreEntityList =  listOf(
        GenreEntityDTO(
            id = 456,
            name = "Drama"
        )
    )
)

val movieDetailDTO = MovieDetailResponseDTO(
    response = movieDTO
)

val movieListDTO = MovieListResponseDTO(
    response = listOf(movieDTO)
)

val movie = Movie(
    id = 12345,
    externalId = "12abc",
    name = "movie name",
    description = "description",
    definition = "HD",
    year = 2017,
    duration = 7691000,
    imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/url"),
    genres = listOf("Drama")
)

val movieRoom = MovieRoomModel(
    id = 12345,
    externalId = "12abc",
    name = "movie name",
    description = "description",
    definition = "HD",
    year = 2017,
    duration = 7691000,
    imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/url"),
    genres = listOf("Drama")
)

val otherMovieDTO = MovieDTO(
    id = 786543,
    externalId = "34dfg",
    name = "Other movie name",
    description = "other description",
    definition = "HD",
    year = 2019,
    duration = 7691000,
    attachments = listOf(
        AttachmentsDTO("/other url")
    ),
    genreEntityList = listOf(
        GenreEntityDTO(
            id = 123,
            name = "Action"
        )
    )
)

val otherMovieDetailDTO = MovieDetailResponseDTO(
    response = otherMovieDTO
)

val otherMovieListDTO = MovieListResponseDTO(
    response = listOf(otherMovieDTO)
)

val otherMovie = Movie(
    id = 786543,
    externalId = "34dfg",
    name = "Other movie name",
    description = "other description",
    definition = "HD",
    year = 2019,
    duration = 7691000,
    imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/other url"),
    genres = listOf("Action")
)

val annotherMovie = Movie(
    id = 786543,
    externalId = "34dfg",
    name = "Other movie name",
    description = "other description",
    definition = "HD",
    year = 2019,
    duration = 7691000,
    imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/other url"),
    genres = listOf("Action")
)

val annotherMovieRoom = MovieRoomModel(
    id = 786543,
    externalId = "34dfg",
    name = "Other movie name",
    description = "other description",
    definition = "HD",
    year = 2019,
    duration = 7691000,
    imagesURL = listOf("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images/other url"),
    genres = listOf("Action")
)

private val showDTO = ShowDTO(
    id = "ert",
    title = "show",
    movieImages = MovieImagesDTO(photo = "photo", poster = "poster", background = "background"),
    actors = emptyList(),
    synopsis = "",
    synopsisEpisode = ""
)

private val show = Show(
    id = "ert",
    title = "show",
    movieImages = MovieImages(photo = "photo", poster = "poster", background = "background"),
    actors = emptyList(),
    synopsis = "",
    synopsisEpisode = ""
)

val genereShowsDTO =  GenereShowsDTO(
    id = "ab12",
    shows = listOf(showDTO, showDTO)
)

val genereShows =  GenereShows(
    id = "ab12",
    shows = listOf(show, show)
)

val otherGenereShowsDTO =  GenereShowsDTO(
    id = "cd45",
    shows = listOf(showDTO, showDTO)
)

val otherGenereShows =  GenereShows(
    id = "cd45",
    shows = listOf(show, show)
)



