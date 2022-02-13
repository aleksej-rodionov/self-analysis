package space.rodionov.selfanalysis.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.data.PreferencesRepository
import space.rodionov.selfanalysis.util.fetchColors

class SettingsViewModel @ViewModelInject constructor(
    private val prefs: PreferencesRepository
): ViewModel() {

    var _colors: Array<Int>? = null

    private val _mode = prefs.modeFlow
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _followSystemMode = prefs.followSystemModeFlow
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun updateMode(mode: Int) = viewModelScope.launch {
        prefs.updateMode(mode)
    }

    fun updateFollowSystemMode(follow: Boolean) = viewModelScope.launch {
        prefs.updateFollowSystemMode(follow)
    }

    fun refreshColors(colors: Array<Int>) {
        _colors = colors
    }

}