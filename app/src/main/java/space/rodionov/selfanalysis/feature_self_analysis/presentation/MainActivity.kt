package space.rodionov.selfanalysis.feature_self_analysis.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.porosenokpetr.ui_compose.theme.SelfAnalysisTheme
import space.rodionov.selfanalysis.feature_self_analysis.edit_add_analysis.presentation.EditAddAnalysisScreen
import space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list.AnalysisListScreen
import space.rodionov.selfanalysis.util.Constants.DEFAULT_INT

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




