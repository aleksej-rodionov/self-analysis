package space.rodionov.selfanalysis.feature_self_analysis.presentation

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
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
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.porosenokpetr.ui_compose.theme.SelfAnalysisTheme
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.databinding.ActivityMainBinding
import space.rodionov.selfanalysis.feature_self_analysis.edit_add_analysis.presentation.EditAddAnalysisScreen
import space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list.AnalysisListScreen
import space.rodionov.selfanalysis.util.Constants.DEFAULT_INT
import space.rodionov.selfanalysis.util.Constants.TAG_MODE
import space.rodionov.selfanalysis.util.ModeConstants.MODE_DARK
import space.rodionov.selfanalysis.util.ModeConstants.MODE_LIGHT
import space.rodionov.selfanalysis.util.fetchColors
import space.rodionov.selfanalysis.util.redrawViewGroup

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContent {
                SelfAnalysisTheme {
                    Surface(
                        color = MaterialTheme.colors.background
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

                            composable(
                                route = Screen.EditAddAnalysisScreen.route + "?analysisId={analysisId}",
                                arguments = listOf(
                                    navArgument(name = "analysisId") {
                                        type = NavType.IntType
                                        defaultValue = DEFAULT_INT
                                    }
                                )
                            ) {
                                EditAddAnalysisScreen(navController = navController)
                            }
                        }
                    }
                }
            }
    }









    

//    private fun setSystemBarsColors(mode: Int) {
//        when (mode) {
//            0 -> {
//                window.statusBarColor = ContextCompat.getColor(this, R.color.ACARed)
//                window.navigationBarColor = ContextCompat.getColor(this, R.color.ACALight)
//
//            }
//            1 -> {
//                window.statusBarColor = ContextCompat.getColor(this, R.color.green)
//                window.navigationBarColor = ContextCompat.getColor(this, R.color.gray900)
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
//
//                val decorView = window.decorView
//                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
//            }
//            else -> {
//                window.statusBarColor = ContextCompat.getColor(this, R.color.ACARed)
//                window.navigationBarColor = ContextCompat.getColor(this, R.color.ACALight)
//            }
//        }
//    }
}




