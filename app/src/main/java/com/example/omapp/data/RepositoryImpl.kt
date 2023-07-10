package com.example.omapp.data

import com.example.omapp.common.DataResponse
import com.example.omapp.data.local.LocalDataSource
import com.example.omapp.data.network.NetworkDataSource
import com.example.omapp.data.network.NetworkDataSourceGenereShows
import com.example.omapp.domain.Repository
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.domain.model.Movie
import java.util.*

const val CACHE_LIFE_TIME = 30000L

class RepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val networkDataSourceGenereShows: NetworkDataSourceGenereShows,
    private val localDataSource: LocalDataSource
) : Repository {

    private val lifetime: Long = CACHE_LIFE_TIME
    private var cacheDate: Date? = null

    override suspend fun getMovieList(page: Int): DataResponse<List<Movie>> {
        cacheDate?.let {
            return if (currentDate().time <= it.time) {
                val result = localDataSource.getMovieList()
                updateSuccessListResponseWithFavorites(result)
            } else {
                localDataSource.invalidate()
                getFromNetwork(page)
            }
        }
        return getFromNetwork(page)

    }

    private suspend fun getFromNetwork(page: Int): DataResponse<List<Movie>> {
        val result = networkDataSource.getMovieList(page)
        return if (result is DataResponse.Success) {
            cacheDate = Date(currentDate().time + lifetime)
            localDataSource.storeMovies(result.data)
            updateSuccessListResponseWithFavorites(result)
        } else result
    }


    override suspend fun getMovieDetail(id: String) =
        when (val result = networkDataSource.getDetail(id)) {
            is DataResponse.Success -> {
                DataResponse.Success(setFavoriteMovieStatus(result.data))
            }
            else -> result
        }

    private suspend fun setFavoriteMovieStatus(it: Movie) =
        if (localDataSource.checkIfFavorite(it.id)) {
            it.copy(isFavorite = true)
        } else {
            it.copy(isFavorite = false)
        }

    private suspend fun updateSuccessListResponseWithFavorites(result: DataResponse<List<Movie>>) =
        if(result is DataResponse.Success){
            DataResponse.Success(result.data.map { setFavoriteMovieStatus(it) })
        } else result



    override suspend fun setFavorite(id: Long, isFavorite: Boolean) =
        localDataSource.setFavoriteMovie(id, isFavorite)


    override var currentDate: () -> Date = { Date() }
    override suspend fun getGenereShow(railId: String) = networkDataSourceGenereShows.getGenereShows(railId)

}