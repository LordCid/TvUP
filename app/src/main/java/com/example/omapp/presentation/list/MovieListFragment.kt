package com.example.omapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.common.presentation.BaseFragment
import com.example.omapp.databinding.FragmentMovieListBinding
import com.example.omapp.domain.model.Movie
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieListFragment : BaseFragment() {

    private var binding: FragmentMovieListBinding? = null

    private val viewModel: MovieListViewModel by viewModel()

    private val imagesLoader: ImagesLoader by inject()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setViewModel()
    }

    private fun initUI() {
        movieAdapter = MovieAdapter(
            imagesLoader = imagesLoader,
            onCLick = onItemClickListener,
            onClickFavorite = onFavoriteClickListener
        )
        binding?.apply {
            with(listView) {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = movieAdapter
            }
        }

        movieAdapter.addLoadStateListener(viewStateListener)

    }

    private var onItemClickListener : (String) -> Unit = {
        findNavController().navigate(
            MovieListFragmentDirections.actionFirstFragmentToSecondFragment(id = it)
        )
    }

    private var onFavoriteClickListener: (Long, Boolean) -> Unit = { id, isFavorite ->
        viewModel.setFavorite(id, isFavorite)
    }


    private fun setViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, ::updateUI)
        lifecycleScope.launch{
            viewModel.getMovies(
//                initialLoadListener = { hideLoadingDialogFragment() },
                scope = this
            )
        }

    }

    private fun updateUI(dataList: PagingData<Movie>) {
        lifecycleScope.launch {
            movieAdapter.submitData(dataList)
        }
    }

    private val viewStateListener: (CombinedLoadStates) -> Unit = {
        when (it.refresh) {
            is LoadState.Error -> showError()
            is LoadState.NotLoading -> {
                hideLoadingDialogFragment()
            }
            is LoadState.Loading -> showLoadingDialogFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun showError() {
        hideLoadingDialogFragment()
        binding?.apply {
            listView.isVisible = false
            errorView.isVisible = true
        }
    }
}
