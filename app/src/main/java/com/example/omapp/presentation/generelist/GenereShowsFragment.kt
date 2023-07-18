package com.example.omapp.presentation.generelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.omapp.common.presentation.BaseFragment
import com.example.omapp.common.presentation.ImagesLoader
import com.example.omapp.databinding.FragmentGenereShowsListBinding
import com.example.omapp.domain.model.GenereShows
import com.example.omapp.presentation.model.GenereShowsUI
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SELECTED_RAIL_ID = "5efb4af4f0e17b0075ced97b"
class GenereShowsFragment  : BaseFragment() {

    private var binding: FragmentGenereShowsListBinding? = null

    private val viewModel: GenereShowsListViewModel by viewModel()

    private val imagesLoader: ImagesLoader by inject()

    private lateinit var genereShowsAdapter: GenereShowsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenereShowsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setViewModel()
    }

    private fun setUpUI() {
        genereShowsAdapter = GenereShowsAdapter(onClickContent, imagesLoader)
        binding?.listView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = genereShowsAdapter
        }
    }

    private val onClickContent: (String) -> Unit = {

    }

    private fun setViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, ::updateUI)
        viewModel.getGenereShows(SELECTED_RAIL_ID)
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


    private fun showData(data: List<GenereShowsUI>) {
        hideLoadingDialogFragment()
        genereShowsAdapter.submitList(data)
    }
}