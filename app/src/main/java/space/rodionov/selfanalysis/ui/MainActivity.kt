package space.rodionov.selfanalysis.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.databinding.ActivityMainBinding
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
                (binding.root as ViewGroup).redrawViewGroup(mode)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}

const val ADD_TASK_RESULT_OK = Activity.RESULT_FIRST_USER
const val EDIT_TASK_RESULT_OK = Activity.RESULT_FIRST_USER + 1




