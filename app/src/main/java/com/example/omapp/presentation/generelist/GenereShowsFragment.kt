package com.example.omapp.presentation.generelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.omapp.R
import com.example.omapp.common.formatDuration
import com.example.omapp.common.presentation.BaseFragment
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.FragmentGenereShowsListBinding
import com.example.omapp.domain.model.Movie
import com.example.omapp.domain.model.Show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenereShowsFragment  : BaseFragment() {

    private var binding: FragmentGenereShowsListBinding? = null

    private val viewModel: GenereShowsListViewModel by viewModel()

    private val imagesLoader: ImagesLoader by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenereShowsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, ::updateUI)
        viewModel.getGenereShows(rail)
    }

    private fun updateUI(viewState: GenereShowsListViewState) {
        when (viewState) {
            is GenereShowsListViewState.Error -> showError(viewState.message)
            GenereShowsListViewState.Loading -> showLoadingDialogFragment()
            is GenereShowsListViewState.ShowMovies -> showData(viewState.data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showError(message: String) {
        hideLoadingDialogFragment()
        showErrorDialog(message)
    }


    private fun showData(data: List<Show>) {
        hideLoadingDialogFragment()
        binding?.apply {
            imagesLoader.loadImage(data.imagesURL.first(), header)
            titleTv.text = data.name
            yearTv.text = data.year.toString()
            durationTv.text = context?.getString(R.string.duration, data.duration.formatDuration())
            descriptionTv.text = data.description
            setFavoriteButton(data.isFavorite)
        }
    }
}