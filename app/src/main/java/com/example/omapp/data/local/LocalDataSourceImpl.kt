package com.example.omapp.data.local

import com.example.omapp.ERROR_DATABASE_GENERIC_MESSAGE
import com.example.omapp.common.DataResponse
import com.example.omapp.common.ErrorResponse
import com.example.omapp.common.Mapper
import com.example.omapp.data.local.room.MovieDao
import com.example.omapp.data.local.room.MovieFavoriteRoomModel
import com.example.omapp.data.local.room.MovieRoomModel
import com.example.omapp.domain.model.Movie


const val NO_AFECTED_ROWS = 0

class LocalDataSourceImpl(
    private val dao: MovieDao,
    private val movieToLocalMapper: Mapper<MovieRoomModel, Movie>,
    private val localToMovieMapper: Mapper<Movie, MovieRoomModel>
) : LocalDataSource {


    override suspend fun storeMovies(movies: List<Movie>) {
        dao.insertMovies(
            movies.map { movieToLocalMapper.map(it) }
        )

    }

    override suspend fun getMovieList(): DataResponse<List<Movie>> {
        dao.getMovies().let {
            return if (it.isNotEmpty())
                DataResponse.Success(
                    it.map { roomModel -> localToMovieMapper.map(roomModel) }
                )
            else
                DataResponse.Failure(ErrorResponse.Unexpected(ERROR_DATABASE_GENERIC_MESSAGE))
        }
    }

    override suspend fun invalidate() {
        dao.deleteMovies()
    }

    override suspend fun setFavoriteMovie(id: Long, isFavorite: Boolean) =
        if (isFavorite) insertFavorite(id) else deleteFavorite(id)


    private suspend fun insertFavorite(id: Long): DataResponse<Boolean> {
        return if (dao.insertFavorite(MovieFavoriteRoomModel(id)) > NO_AFECTED_ROWS) {
            DataResponse.Success(true)
        } else DataResponse.Failure(ErrorResponse.Unexpected(ERROR_DATABASE_GENERIC_MESSAGE))
    }


    private suspend fun deleteFavorite(id: Long): DataResponse<Boolean> {
        return if (dao.deleteFavorite(MovieFavoriteRoomModel(id)) > NO_AFECTED_ROWS) {
            DataResponse.Success(false)
        } else DataResponse.Failure(ErrorResponse.Unexpected(ERROR_DATABASE_GENERIC_MESSAGE))
    }


    override suspend fun checkIfFavorite(id: Long) = dao.checkIfFavorite(id) != null

}