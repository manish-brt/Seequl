package com.app.seequl.ui.modules.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.seequl.constants.EXTRA_MOVIE_ID
import com.app.seequl.databinding.FragmentBookmarkBinding
import com.app.seequl.extensions.launchActivity
import com.app.seequl.ui.adapters.BookmarkAdapter
import com.app.seequl.ui.modules.details.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null

    private val mViewModel: BookmarkViewModel by viewModels()

    private val binding get() = _binding!!
    private lateinit var adapter: BookmarkAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.viewModel = mViewModel
        binding.lifecycleOwner = this

        adapter = BookmarkAdapter()
        binding.recyclerView.adapter = adapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    private fun initObservers() {
        mViewModel.openDetails.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                requireContext().launchActivity<MovieDetailsActivity> {
                    putExtra(EXTRA_MOVIE_ID, it.id)
                }
            }
        }

        adapter

        adapter.addDeleteListener {
            mViewModel.deleteBookmark(it)
        }

        mViewModel.bookmarkedMovies.observe(viewLifecycleOwner) { bookmarks ->
            if (bookmarks != null)
                adapter.setItems(bookmarks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}