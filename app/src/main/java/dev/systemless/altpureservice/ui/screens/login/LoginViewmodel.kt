package dev.systemless.altpureservice.ui.screens.login

import android.content.Context
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.systemless.altpureservice.data.local.SettingsStore
import dev.systemless.altpureservice.util.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    val settingsStore: SettingsStore
) : ViewModel() {


    val apiUrlState = TextFieldState()
    fun onButtonClick(context: Context) {
        val apiurl = apiUrlState.text.toString() + "/login?ReturnUrl=pureservicemobile"
        if (apiurl.isNotBlank()) {
            viewModelScope.launch {
                settingsStore.setPureserviceUrl(apiurl)
            }
            Log.i("pureservicegood", apiurl)
            val intent = CustomTabsIntent.Builder()
                .build()
            intent.launchUrl(
                context,
                apiurl.toUri()
            )
        }
    }
}
