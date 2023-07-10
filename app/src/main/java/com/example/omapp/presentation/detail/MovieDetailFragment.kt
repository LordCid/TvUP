package com.example.omapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.omapp.R
import com.example.omapp.common.formatDuration
import com.example.omapp.common.presentation.BaseFragment
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.FragmentMovieDetailBinding
import com.example.omapp.domain.model.Movie
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment() {

    private var binding: FragmentMovieDetailBinding? = null

    private val viewModel: MovieDetailViewModel by viewModel()

    private val imagesLoader: ImagesLoader by inject()

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, ::updateUI)
        viewModel.getMovieDetail(args.id)
    }

    private fun updateUI(viewState: MovieDetailViewState) {
        when (viewState) {
            is MovieDetailViewState.Error -> showError(viewState.message)
            MovieDetailViewState.Loading -> showLoadingDialogFragment()
            is MovieDetailViewState.ShowMovies -> showData(viewState.data)
            is MovieDetailViewState.FavoriteUpdate -> updateFavoriteButton(viewState.isFavorite)
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding?.apply {
            favouriteButton.setImageResource(getFavoriteIcon(isFavorite))
        }
    }

    private fun showError(message: String) {
        hideLoadingDialogFragment()
        showErrorDialog(message)
    }


    private fun showData(data: Movie) {
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

    private fun setFavoriteButton(isFavorite: Boolean) {
        binding?.favouriteButton?.apply {
            setImageResource(getFavoriteIcon(isFavorite))
            setOnClickListener { viewModel.setFavorite() }
        }
    }

    private fun getFavoriteIcon(isFavorite: Boolean): Int {
        return if(isFavorite) R.drawable.ic_favorite_black else R.drawable.ic_favorite_border_black
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}