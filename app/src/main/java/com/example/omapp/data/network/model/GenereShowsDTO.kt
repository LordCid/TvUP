package com.example.omapp.data.network.model

import android.graphics.Picture
import com.google.gson.annotations.SerializedName

data class GenereShowsDTO(
    @SerializedName("genre") val id: String,
    @SerializedName("shows") val shows: List<ShowDTO>
)

data class  ShowDTO(
    @SerializedName("eventId") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("moviePictures") val movieImages: MovieImagesDTO,
    @SerializedName("actors") val actors: List<String>,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("synopsisEpisode") val synopsisEpisode: String
)

data class MovieImagesDTO(
    @SerializedName("photo") val photo: String,
    @SerializedName("poster") val poster: String,
    @SerializedName("background") val background: String,
)