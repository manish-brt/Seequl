package com.app.seequl.ui.modules.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.app.seequl.constants.EXTRA_MOVIE_ID
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.databinding.FragmentSearchBinding
import com.app.seequl.extensions.launchActivity
import com.app.seequl.extensions.setDebouncedTextListener
import com.app.seequl.ui.modules.details.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    val mViewModel: SearchViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.vModel = mViewModel
        binding.lifecycleOwner = this

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()

    }

    private fun initObservers() {

        mViewModel.openDetails.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                launchDetailsScreen(it)
                mViewModel.resetDetailsData()
            }
        }
    }

    private fun initListeners() {

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_SEARCH
            ) {

                if (isSearchStrAndValid()) {
                    mViewModel.resetPage()

                    mViewModel.saveSearchedString(mViewModel.searchStr.value)
                    hideSoftInput(binding.etSearch)
                    binding.etSearch.clearFocus()
                    mViewModel.searchMovies()
                }
            }
            false
        }

//        binding.etSearch.setDebouncedTextListener(500) { query ->
//            {
//                if (query.isBlank() || query.isEmpty()) {
//                    binding.searchTextSwitcher.isVisible = true
//                    binding.searchTextPlaceholder.isVisible = true
//                } else {
//                    binding.searchTextSwitcher.isVisible = false
//                    binding.searchTextPlaceholder.isVisible = false
////                hideSoftInput(binding.etSearch)
////                binding.etSearch.clearFocus()
//                }
//
//                mViewModel.resetPage()
//                mViewModel.saveSearchedString(query)
//                mViewModel.searchMovies()
//            }
//        }

        binding.etSearch.doAfterTextChanged {
            if (it.isNullOrBlank() || it.isEmpty()) {
                binding.searchTextSwitcher.isVisible = true
                binding.searchTextPlaceholder.isVisible = true
            } else {
                binding.searchTextSwitcher.isVisible = false
                binding.searchTextPlaceholder.isVisible = false
            }

            mViewModel.resetPage()
            mViewModel.saveSearchedString(it.toString())
            mViewModel.searchMovies()

        }
    }

    private fun hideSoftInput(editText: EditText) {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun isSearchStrAndValid(): Boolean {
        return mViewModel.searchStr.value.isNullOrBlank().not()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun launchDetailsScreen(dto: MovieDTO) {
        requireContext().launchActivity<MovieDetailsActivity> {
            putExtra(EXTRA_MOVIE_ID, dto.id)
        }
    }
}