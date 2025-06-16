package com.app.seequl.ui.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.seequl.constants.EXTRA_MOVIE_ID
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.databinding.FragmentHomeBinding
import com.app.seequl.extensions.launchActivity
import com.app.seequl.ui.adapters.CarouselAdapter
import com.app.seequl.ui.modules.details.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mViewModel: HomeViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.viewModel = mViewModel
        binding.lifecycleOwner = this

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        mViewModel.fetchTrendingMovies()
        mViewModel.fetchNowShowingMovies()
        mViewModel.fetchTopRatedMovies()

    }

    fun initObservers() {

        mViewModel.trendingMovies.observe(viewLifecycleOwner) { it ->
            if (it.isNotEmpty())
                loadCarousel(it)
        }

        mViewModel.nowShowingMovies.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                mViewModel.nowShowingAdapter.updateList(it)
                binding.llNowShowing.isVisible = true
            }
        }

        mViewModel.topRatedMovies.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                mViewModel.topRatedAdapter.updateList(it)
                binding.llTopRated.isVisible = true
            }
        }

        mViewModel.openDetails.observe(viewLifecycleOwner) { it ->
            if (it != null)
                launchDetailsScreen(it)
        }

//        mViewModel.text.observe(viewLifecycleOwner) {
//            binding.textDashboard.text = it
//        }
    }

    private fun loadCarousel(list: List<MovieDTO>) {
        if (list.isEmpty()) {
            binding.carouselContainer.isVisible = false
            return
        } else {
            val cCount = list.size
            binding.carouselContainer.isVisible = true

            val carouselAdapter = CarouselAdapter()
            carouselAdapter.updateList(list)

            carouselAdapter.addListener {
                launchDetailsScreen(it)
            }

            binding.viewPager.apply {
                adapter = carouselAdapter
                offscreenPageLimit = if (cCount > 1) 3 else 1
                pageMargin = 4
                clipToPadding = false
                clipChildren = false

                // increase this offset to show more of left/right
//            setPadding(12.toPx, 0, if (dto.clientApps.size == 1) 12.toPx else 48.toPx, 0)

                // Set the new height
//                postDelayed({
//                    val newHeight = (binding.viewPager.width * 9.0 / 16.0).toInt()
//                    val layoutParams = layoutParams
//                    layoutParams.height = newHeight
//                    setLayoutParams(layoutParams)
//                    requestLayout()
//                }, 300)
            }

            binding.dotsIndicator.apply {
                if (cCount > 1) {
                    isVisible = true
                    attachToPager(binding.viewPager, cCount)
                } else isVisible = false
            }

            if (cCount > 1)
                binding.viewPager.startAutoScroll()
        }
    }

    fun launchDetailsScreen(dto: MovieDTO) {
        requireContext().launchActivity<MovieDetailsActivity> {
            putExtra(EXTRA_MOVIE_ID, dto.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}