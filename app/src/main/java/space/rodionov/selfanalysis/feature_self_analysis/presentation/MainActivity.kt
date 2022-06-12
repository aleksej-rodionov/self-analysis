package space.rodionov.selfanalysis.feature_self_analysis.presentation

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.ui_compose.theme.SelfAnalysisTheme
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.databinding.ActivityMainBinding
import space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list.AnalysisListScreen
import space.rodionov.selfanalysis.util.Constants.TAG_MODE
import space.rodionov.selfanalysis.util.ModeConstants.MODE_DARK
import space.rodionov.selfanalysis.util.ModeConstants.MODE_LIGHT
import space.rodionov.selfanalysis.util.fetchColors
import space.rodionov.selfanalysis.util.redrawViewGroup

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SelfAnalysisTheme {
                Surface(
                    color =MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.AnalysisListScreen.route
                    ) {
                        composable(
                            route = Screen.AnalysisListScreen.route
                        ) {
                            AnalysisListScreen(navController = navController)
                        }
                    }
                }
            }
        }

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.findNavController()
//
//        setupActionBarWithNavController(navController)
//
//        this.lifecycleScope.launchWhenStarted {
//            viewModel.mode.collectLatest {
//                val mode = it?: return@collectLatest
//                setSystemBarsColors(mode)
//                val colors = fetchColors(mode, resources)
//                supportActionBar?.setBackgroundDrawable(colors[4].toDrawable())
//                (binding.root as ViewGroup).redrawViewGroup(mode)
//            }
//        }
//
//        this.lifecycleScope.launchWhenStarted {
//            viewModel.followSystemMode.collectLatest {
//                val follow = it?: return@collectLatest
//                if (follow) viewModel.updateMode(getSystemTheme())
//            }
//        }
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        Log.d(TAG_MODE, "onConfigurationChanged: followSM = ${viewModel.checkFollowingSystemTheme()}")
//        if (viewModel.checkFollowingSystemTheme()) viewModel.updateMode(getSystemTheme())
//    }
//
//    private fun getSystemTheme() :Int {
//        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_YES -> MODE_DARK
//            Configuration.UI_MODE_NIGHT_NO -> MODE_LIGHT
//            else -> MODE_LIGHT
//        }
//    }

    private fun setSystemBarsColors(mode: Int) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        when (mode) {
            0 -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.ACARed)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.ACALight)
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            }
            1 -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.green)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.gray900)
                window.decorView.systemUiVisibility = /*View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        */View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

                val decorView = window.decorView
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
            }
            else -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.ACARed)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.ACALight)
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
//        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}

//const val ADD_TASK_RESULT_OK = Activity.RESULT_FIRST_USER
//const val EDIT_TASK_RESULT_OK = Activity.RESULT_FIRST_USER + 1




