package dev.systemless.altpureservice.ui.screens.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.systemless.altpureservice.data.local.SettingsStore
import javax.inject.Inject

@HiltViewModel
class SearchViewmodel @Inject constructor(
    val settingsStore: SettingsStore
) : ViewModel() {

}
