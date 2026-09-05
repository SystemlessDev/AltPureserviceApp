package dev.systemless.altpureservice.ui.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.systemless.altpureservice.data.local.SettingsStore
import javax.inject.Inject

@HiltViewModel
class SettingsViewmodel @Inject constructor(
    val settingsStore: SettingsStore
) : ViewModel() {

}
