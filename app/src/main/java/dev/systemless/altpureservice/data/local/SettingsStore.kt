package dev.systemless.altpureservice.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.Datastore: DataStore<Preferences> by preferencesDataStore("userData")
        private val PURESERVICE_TOKEN = stringPreferencesKey("PURESERVICE_TOKEN")
        private val PURESERVICE_API_URL = stringPreferencesKey("BASE_URL")
    }

    val getPureserviceToken: Flow<String> = context.Datastore.data.map {
        preferences -> preferences[PURESERVICE_TOKEN] ?: ""
    }

    suspend fun setPureserviceToken(token: String) {
        context.Datastore.edit { preferences ->
            preferences[PURESERVICE_TOKEN] = token
        }
    }

    val getPureserviceBaseurl: Flow<String> = context.Datastore.data.map {
            preferences -> preferences[PURESERVICE_API_URL] ?: ""
    }

    suspend fun setPureserviceUrl(apiUrl: String) {
        context.Datastore.edit { preferences ->
            preferences[PURESERVICE_API_URL] = apiUrl
        }
    }
}
