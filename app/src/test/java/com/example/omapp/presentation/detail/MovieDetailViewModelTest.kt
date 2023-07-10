package com.example.omapp.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.omapp.ERROR_GENERIC_MESSAGE
import com.example.omapp.common.DataResponse
import com.example.omapp.common.ErrorResponse
import com.example.omapp.domain.GetMovieDetailUseCase
import com.example.omapp.domain.SetFavoriteMovieUseCase
import com.example.omapp.movie
import com.example.omapp.otherMovie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

    private lateinit var sut: MovieDetailViewModel
    private val observer = mockk<Observer<MovieDetailViewState>>(relaxed = true)
    private val getMovieDetail = mockk<GetMovieDetailUseCase>()
    private val setFavoriteMovieUseCase = mockk<SetFavoriteMovieUseCase>()

    @ExperimentalCoroutinesApi
    private val dispatcher = UnconfinedTestDispatcher()

    private val captor = mutableListOf<MovieDetailViewState>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        sut = MovieDetailViewModel(getMovieDetail, setFavoriteMovieUseCase, dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN get Movie detail THEN Loading ViewState`() {
        val id = "ab123"

        sut.viewState.observeForever(observer)
        sut.getMovieDetail(id)

        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[0] is MovieDetailViewState.Loading)
    }

    @Test
    fun `GIVEN success response WHEN get Movie Detail THEN Show Data View State`() {
        val id = "ab123"
        val expected = movie
        coEvery { getMovieDetail.invoke(any()) } returns flow { emit(DataResponse.Success(expected)) }

        sut.viewState.observeForever(observer)
        sut.getMovieDetail(id)


        coVerify { getMovieDetail.invoke(id) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.ShowMovies)
        val viewState = captor[1] as MovieDetailViewState.ShowMovies
        assertEquals(expected, viewState.data)
    }


    @Test
    fun `GIVEN OTHER success response WHEN get Movie Detail THEN Show Data View State`() {
        val id = "cd456"
        val expected = otherMovie
        coEvery { getMovieDetail.invoke(any()) } returns flow { emit(DataResponse.Success(expected)) }

        sut.viewState.observeForever(observer)
        sut.getMovieDetail(id)


        coVerify { getMovieDetail.invoke(id) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.ShowMovies)
        val viewState = captor[1] as MovieDetailViewState.ShowMovies
        assertEquals(expected, viewState.data)
    }

    @Test
    fun `GIVEN Failure response WHEN get Movie Detail THEN Error View State`() {
        val id = "ab123"
        val expected = ErrorResponse.Unexpected("error Message")
        coEvery { getMovieDetail.invoke(any()) } returns flow { emit(DataResponse.Failure(expected)) }

        sut.viewState.observeForever(observer)
        sut.getMovieDetail(id)


        coVerify { getMovieDetail.invoke(id) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.Error)
        val viewState = captor[1] as MovieDetailViewState.Error
        assertEquals(expected.message, viewState.message)
    }

    @Test
    fun `GIVEN any other exception WHEN get Movie Detail THEN Error View State`() {
        val id = "ab123"
        val expected = ERROR_GENERIC_MESSAGE
        coEvery { getMovieDetail.invoke(any()) } returns flow { throw Throwable()}

        sut.viewState.observeForever(observer)
        sut.getMovieDetail(id)


        coVerify { getMovieDetail.invoke(id) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.Error)
        val viewState = captor[1] as MovieDetailViewState.Error
        assertEquals(expected, viewState.message)
    }

    @Test
    fun `GIVEN set Favorite succeds WHEN set Favorite movie true THEN Set Favorite Update View State true`() {
        val id = 12345L
        val isFavorite = true
        val expected = DataResponse.Success(isFavorite)
        coEvery { setFavoriteMovieUseCase.invoke(any(), any()) } returns expected
        givenPreviouslyFetchedMovieDetail(false)

        sut.viewState.observeForever(observer)
        sut.setFavorite()

        coVerify { setFavoriteMovieUseCase.invoke(id, isFavorite) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.FavoriteUpdate)
        val viewState = captor[1] as MovieDetailViewState.FavoriteUpdate
        assertEquals(isFavorite, viewState.isFavorite)
    }

    @Test
    fun `GIVEN set Favorite succeds WHEN set Favorite movie false THEN Set Favorite Update View State false`() {
        val id = 12345L
        val isFavorite = false
        val expected = DataResponse.Success(isFavorite)
        coEvery { setFavoriteMovieUseCase.invoke(any(), any()) } returns expected
        givenPreviouslyFetchedMovieDetail(true)

        sut.viewState.observeForever(observer)
        sut.setFavorite()

        coVerify { setFavoriteMovieUseCase.invoke(id, isFavorite) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.FavoriteUpdate)
        val viewState = captor[1] as MovieDetailViewState.FavoriteUpdate
        assertEquals(isFavorite, viewState.isFavorite)
    }

    @Test
    fun `GIVEN set Favorite fails WHEN set Favorite movie THEN Error View State`() {
        val id = 12345L
        val isFavorite = false
        val expected = ErrorResponse.Unexpected("Error message")
        coEvery { setFavoriteMovieUseCase.invoke(any(), any()) } returns DataResponse.Failure(expected)
        givenPreviouslyFetchedMovieDetail(true)

        sut.viewState.observeForever(observer)
        sut.setFavorite()

        coVerify { setFavoriteMovieUseCase.invoke(id, isFavorite) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.Error)
        val viewState = captor[1] as MovieDetailViewState.Error
        assertEquals(expected.message, viewState.message)
    }

    @Test
    fun `GIVEN check favorite WHEN set Favorite movie THEN Error View State`() {
        val id = 12345L
        val isFavorite = false
        val expected = ErrorResponse.Unexpected("Error message")
        coEvery { setFavoriteMovieUseCase.invoke(any(), any()) } returns DataResponse.Failure(expected)
        givenPreviouslyFetchedMovieDetail(true)

        sut.viewState.observeForever(observer)
        sut.setFavorite()

        coVerify { setFavoriteMovieUseCase.invoke(id, isFavorite) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is MovieDetailViewState.Error)
        val viewState = captor[1] as MovieDetailViewState.Error
        assertEquals(expected.message, viewState.message)
    }

    private fun givenPreviouslyFetchedMovieDetail(newFavoriteStatus: Boolean) {
        val expected = movie.copy(isFavorite = newFavoriteStatus)
        coEvery { getMovieDetail.invoke(any()) } returns flow { emit(DataResponse.Success(expected)) }
        sut.getMovieDetail("some id")
    }

}