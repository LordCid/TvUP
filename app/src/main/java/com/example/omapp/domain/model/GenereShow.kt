package com.example.omapp.domain.model

import com.google.gson.annotations.SerializedName

data class GenereShows(
    val id: String,
    val shows: List<Show>
)

data class Show(
    val id: String,
    val title: String,
    val movieImages: MovieImages,
    val actors: List<String>,
    val synopsis: String,
    val synopsisEpisode: String
)

data class MovieImages(
    val photo: String,
    val poster: String,
    val background: String
)