package com.example.omapp.data

import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.domain.Repository

class RepositoryImpl(
    private val networkDataSourceGenereShows: NetworkDataSourceGenereShows
) : Repository {
    override suspend fun getGenereShow(railId: String) = networkDataSourceGenereShows.getGenereShows(railId)

}