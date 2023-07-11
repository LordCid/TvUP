package com.example.omapp.presentation.generelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.omapp.common.ErrorResponse
import com.example.omapp.domain.GetGenereShowsUseCase
import com.example.omapp.genereShows
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GenereShowsListViewModelTest {

    private lateinit var sut : GenereShowsListViewModel

    private val observer = mockk<Observer<GenereShowsListViewState>>(relaxed = true)
    private val getGenereShowsUseCase = mockk<GetGenereShowsUseCase>()



    private val captor = mutableListOf<GenereShowsListViewState>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        sut = GenereShowsListViewModel(getGenereShowsUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN get Genere shows THEN Loading ViewState`() {
        val railId = "ab123"

        sut.viewState.observeForever(observer)
        sut.getGenereShows(railId)

        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[0] is GenereShowsListViewState.Loading)
    }

    @Test
    fun `GIVEN success response WHEN get Genere shows THEN Show Data View State`() {
        val railId = "ab123"
        val expected = listOf(genereShows)
        coEvery { getGenereShowsUseCase.invoke(any()) } returns flow { emit(expected) }

        sut.viewState.observeForever(observer)
        sut.getGenereShows(railId)


        coVerify { getGenereShowsUseCase.invoke(railId) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is GenereShowsListViewState.ShowMovies)
        val viewState = captor[1] as GenereShowsListViewState.ShowMovies
        assertEquals(expected, viewState.data)
    }

    @Test
    fun `GIVEN failure response WHEN get Genere shows THEN Error View State`() {
        val railId = "ab123"
        val errorMessage = "error"
        coEvery { getGenereShowsUseCase.invoke(any()) } returns flow { throw ErrorResponse.Unexpected(errorMessage) }

        sut.viewState.observeForever(observer)
        sut.getGenereShows(railId)


        coVerify { getGenereShowsUseCase.invoke(railId) }
        verify { observer.onChanged(capture(captor)) }
        assertTrue(captor[1] is GenereShowsListViewState.Error)
        val viewState = captor[1] as GenereShowsListViewState.Error
        assertEquals(errorMessage, viewState.message)
    }

}