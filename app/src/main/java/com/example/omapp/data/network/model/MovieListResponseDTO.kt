package com.example.omapp.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieListResponseDTO(
    @SerializedName("response") val response : List<MovieDTO>
)
