package com.example.omapp.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "Movies",
    primaryKeys = ["id"],
)
data class MovieRoomModel(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "externalId") val externalId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "definition") val definition: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "imagesURL") val imagesURL : List<String>,
    @ColumnInfo(name = "genres") val genres : List<String>
)
