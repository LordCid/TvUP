package com.example.omapp.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("externalId") val externalId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("definition") val definition: String,
    @SerializedName("year") val year: Int,
    @SerializedName("duration") val duration: Long,
    @SerializedName("attachments") val attachments: List<AttachmentsDTO>,
    @SerializedName("genreEntityList") val genreEntityList: List<GenreEntityDTO>,
)
