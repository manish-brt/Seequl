package com.app.seequl.ui.modules.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.seequl.constants.EXTRA_MOVIE_ID
import com.app.seequl.databinding.ActivityMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import com.app.seequl.constants.GITHUB_SEEQUL_PAGE
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    private val mViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vModel = mViewModel
        binding.lifecycleOwner = this

        initObservers()
        initListeners()
        decodeIntent()
    }

    private fun decodeIntent() {
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        if (movieId >= 0) {
            mViewModel.fetchMovieDetails(movieId)
        }
    }

    private fun initObservers() {
        mViewModel.details.observe(this) {
            if (it != null)
                binding.item = it
        }
    }

    private fun initListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.favoriteBtn.setOnClickListener {
            mViewModel.toggleBookmark()
        }

        binding.playButton.setOnClickListener {
            openYouTubeTrailer(this, mViewModel.details.value?.title ?: "")
        }

        binding.trailerButton.setOnClickListener {
            openYouTubeTrailer(this, mViewModel.details.value?.title ?: "")
        }

        binding.shareBtn.setOnClickListener {
            shareDeeplink(this, mViewModel.details.value)
        }
    }


    fun openYouTubeTrailer(context: Context, movieTitle: String) {
        val query = Uri.encode("$movieTitle trailer")
        val intent = Intent(Intent.ACTION_SEARCH).apply {
            setPackage("com.google.android.youtube")
            putExtra("query", query)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // open in browser if YouTube app is not available
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                "https://www.youtube.com/results?search_query=$query".toUri()
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(webIntent)
        }
    }

    fun shareDeeplink(context: Context, movie: MovieDetails?) {
        val deepLink = "$GITHUB_SEEQUL_PAGE?movieId=${movie?.id}"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${movie?.title}\nCheck this movie: $deepLink")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share via")
        context.startActivity(shareIntent)
    }

}