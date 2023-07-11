package com.example.omapp

import com.example.omapp.data.network.model.*
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.MovieImages
import com.example.omapp.domain.model.Show


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
    movieImages = MovieImages(photo = "https://mediasync.tvup.cloud/photo", poster = "https://mediasync.tvup.cloud/poster", background = "https://mediasync.tvup.cloud/background"),
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
