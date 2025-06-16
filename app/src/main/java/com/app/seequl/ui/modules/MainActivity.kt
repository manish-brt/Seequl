package com.app.seequl.ui.modules

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.seequl.R
import com.app.seequl.constants.EXTRA_MOVIE_ID
import com.app.seequl.databinding.ActivityMainBinding
import com.app.seequl.extensions.launchActivity
import com.app.seequl.ui.modules.details.MovieDetailsActivity
import com.app.seequl.utils.LogUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
//            val bars = insets.getInsets(
//                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
//            )
//            v.updatePadding(
//                left = bars.left,
//                top = bars.top,
//                right = bars.right,
//                bottom = bars.bottom
//            )
//            WindowInsetsCompat.CONSUMED
//        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//

        navView.setupWithNavController(navController)

        intent.data?.let { handleDeeplink(it) }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { handleDeeplink(it) }
    }

    private fun handleDeeplink(uri: Uri) {
        val movieId = uri.lastPathSegment
        launchActivity<MovieDetailsActivity> {
            putExtra(EXTRA_MOVIE_ID, movieId?.toInt())
        }
    }
}