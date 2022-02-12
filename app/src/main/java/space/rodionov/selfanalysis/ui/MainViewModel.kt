package space.rodionov.selfanalysis.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import space.rodionov.selfanalysis.data.PreferencesRepository

class MainViewModel @ViewModelInject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _mode = preferencesRepository.modeFlow
    val mode = _mode.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _followSystemMode = preferencesRepository.followSystemModeFlow
    val followSystemMode = _followSystemMode.stateIn(viewModelScope, SharingStarted.Lazily, null)


}