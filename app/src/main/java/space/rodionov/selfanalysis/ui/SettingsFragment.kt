package space.rodionov.selfanalysis.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.databinding.FragmentSettingsBinding
import space.rodionov.selfanalysis.util.ModeConstants.MODE_DARK
import space.rodionov.selfanalysis.util.ModeConstants.MODE_LIGHT
import space.rodionov.selfanalysis.util.fetchColors
import space.rodionov.selfanalysis.util.redrawViewGroup

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val vmSettings: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        binding.apply {


            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmSettings.mode.collectLatest {
                    val mode = it?: return@collectLatest
                    (binding.root as ViewGroup).redrawViewGroup(mode)
                    val colors = fetchColors(mode, resources)
                    vmSettings.refreshColors(colors)

                    switchMode.setOnCheckedChangeListener(null)
                    switchMode.isChecked = mode == MODE_DARK
                    switchMode.setOnCheckedChangeListener { _, isChecked ->
                        val modeId = if (isChecked) MODE_DARK else MODE_LIGHT
                        vmSettings.followSystemMode.value?.let { follow ->
                            if (!follow) {
                                vmSettings.updateMode(modeId)
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                vmSettings.followSystemMode.collectLatest {
                    val follow = it ?: return@collectLatest

                    switchMode.isEnabled = !follow
                    if (follow) {
                        switchMode.apply {
                            setTextColor(resources.getColor(R.color.textGray))
                            vmSettings._colors?.let { colors -> thumbTintList = ColorStateList.valueOf(colors[5]) }
                        }
                    } else {
                        switchMode.apply {
                            vmSettings._colors?.let { colors-> setTextColor(colors[2]) }
                            thumbTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
                        }
                    }

                    switchSystemMode.setOnCheckedChangeListener(null)
                    switchSystemMode.isChecked = follow
                    switchSystemMode.setOnCheckedChangeListener { _, isChecked ->
                        vmSettings.updateFollowSystemMode(isChecked)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}