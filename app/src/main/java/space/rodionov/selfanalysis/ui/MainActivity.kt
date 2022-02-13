package space.rodionov.selfanalysis.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.databinding.ActivityMainBinding
import space.rodionov.selfanalysis.util.fetchColors
import space.rodionov.selfanalysis.util.redrawViewGroup

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)

        this.lifecycleScope.launchWhenStarted {
            viewModel.mode.collectLatest {
                val mode = it?: return@collectLatest
                setSystemBarsColors(mode)
                val colors = fetchColors(mode, resources)
                supportActionBar?.setBackgroundDrawable(colors[4].toDrawable())
                (binding.root as ViewGroup).redrawViewGroup(mode)
            }
        }
    }

    private fun setSystemBarsColors(mode: Int) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        when (mode) {
            0 -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.ACARed)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.ACALight)
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            }
            1 -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray900)
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

const val ADD_TASK_RESULT_OK = Activity.RESULT_FIRST_USER
const val EDIT_TASK_RESULT_OK = Activity.RESULT_FIRST_USER + 1




