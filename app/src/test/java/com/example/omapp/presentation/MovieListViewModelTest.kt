package com.example.omapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.map
import com.example.omapp.common.DataResponse
import com.example.omapp.domain.GetMoviesUseCase
import com.example.omapp.domain.SetFavoriteMovieUseCase
import com.example.omapp.domain.model.Movie
import com.example.omapp.movie
import com.example.omapp.presentation.list.MovieListViewModel
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*

class MovieListViewModelTest {

    private lateinit var sut: MovieListViewModel


    private val observer = mockk<Observer<PagingData<Movie>>>(relaxed = true)
    private val getMoviesUseCase = mockk<GetMoviesUseCase>(relaxed = true)
    private val setFavoriteMovieUseCase = mockk<SetFavoriteMovieUseCase>(relaxed = true)

    @ExperimentalCoroutinesApi
    private val scope = TestScope()
    @ExperimentalCoroutinesApi
    private val dispatcher = UnconfinedTestDispatcher()

    private val captor = slot<PagingData<Movie>>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        sut = MovieListViewModel(getMoviesUseCase, setFavoriteMovieUseCase, dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN paged movie results WHEN get Movies THEN correct result is returned`() {
        runBlocking {
            val expected = movie
            coEvery { getMoviesUseCase.invoke(any()) } returns flow { emit(PagingData.from(listOf(expected))) }

            sut.viewState.observeForever(observer)
            sut.getMovies(scope)


            verify { observer.onChanged(capture(captor)) }
            coVerify { getMoviesUseCase.invoke(scope) }
            captor.captured.map { assertEquals(expected, it) }

        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN OTHER paged movie results WHEN get Movies THEN correct result is returned`() {
        runBlocking {
            val expected = movie
            coEvery { getMoviesUseCase.invoke(any()) } returns flow { emit(PagingData.from(listOf(expected))) }

            sut.viewState.observeForever(observer)
            sut.getMovies(scope)


            verify { observer.onChanged(capture(captor)) }
            coVerify { getMoviesUseCase.invoke(scope) }
            captor.captured.map { assertEquals(expected, it) }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN movie id and favorite true WHEN set Favorite THEN Use case is invoked and list is reloaded`() {
        val id = 1234L
        val isFavorite = true
        coEvery { setFavoriteMovieUseCase.invoke(any(), any()) } returns DataResponse.Success(isFavorite)

        sut.setFavorite(id, isFavorite)

        coVerify { setFavoriteMovieUseCase.invoke(id, isFavorite) }
        coVerify { getMoviesUseCase.invoke(any()) }

    }

}