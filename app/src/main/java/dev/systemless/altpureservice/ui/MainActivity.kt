package dev.systemless.altpureservice.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import dev.systemless.altpureservice.data.local.SettingsStore
import dev.systemless.altpureservice.data.remote.BaseUrlInterceptor
import dev.systemless.altpureservice.data.remote.CookieInterceptor
import dev.systemless.altpureservice.ui.screens.filter_list.FilterListScreen
import dev.systemless.altpureservice.ui.screens.login.LoginScreen
import dev.systemless.altpureservice.ui.screens.search.SearchScreen
import dev.systemless.altpureservice.ui.screens.settings.SettingsScreen
import dev.systemless.altpureservice.ui.screens.ticket_details.TicketDetailScreen
import dev.systemless.altpureservice.ui.screens.ticket_details.TicketDetailViewModel
import dev.systemless.altpureservice.ui.screens.ticket_list.TicketListScreen
import dev.systemless.altpureservice.ui.screens.ticket_list.TicketListViewModel
import dev.systemless.altpureservice.ui.theme.altpureserviceTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var baseUrlInterceptor: BaseUrlInterceptor

    @Inject
    lateinit var cookieInterceptor: CookieInterceptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val store = SettingsStore(this)
        val data: String? = intent?.data?.encodedQuery
        CoroutineScope(Dispatchers.IO).launch {
            if (!data.isNullOrBlank()) {
                data.split("&").forEach {
                    val queryParam = it.split("=")
                    when (queryParam[0]) {
                        ".Ps.Auth" -> {
                            store.setPureserviceToken(queryParam[1])
                        }

                        else -> {
                            Log.w(
                                "PureserviceDeeplinkHandler",
                                "Ignoring field ${queryParam[0]} with value ${queryParam[1]}"
                            )
                        }
                    }
                }
            }
        }

        setContent {
            altpureserviceTheme {
                val backStack = remember { mutableStateListOf<Any>(FilterList) }
                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

                val baseUrl = store.getPureserviceBaseurl.collectAsState("")
                val token = store.getPureserviceToken.collectAsState("")

                if (token.value.isBlank() || baseUrl.value.isBlank()) {
                    LoginScreen()
                } else {
                    baseUrlInterceptor.setBaseUrl(baseUrl.value)
                    cookieInterceptor.setAuthenticationToken(token.value)

                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                bottomBarState.value,
                                exit = slideOutVertically() + shrinkVertically(),
                                enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top)
                            ) {
                                NavigationBar() {
                                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                                        val isSelected = topLevelRoute == backStack.last()
                                        NavigationBarItem(selected = isSelected, onClick = {
                                            backStack.add(topLevelRoute)
                                        }, icon = {
                                            Icon(
                                                painter = painterResource(topLevelRoute.icon), ""
                                            )
                                        })
                                    }
                                }
                            }
                        }) { padding ->
                        NavDisplay(
                            backStack = backStack,
                            onBack = { backStack.removeLastOrNull() },
                            modifier = Modifier.padding(padding),
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator(),
                            ),

                            entryProvider = { key ->
                                when (key) {
                                    is Login -> NavEntry(key) {
                                        bottomBarState.value = false
                                        LoginScreen()
                                    }

                                    is FilterList -> NavEntry(key) {
                                        bottomBarState.value = true
                                        FilterListScreen(onClick = { data ->
                                            backStack.add(TicketList(data.name, data.filter))
                                        })
                                    }

                                    is TicketList -> NavEntry(key) {
                                        bottomBarState.value = true
                                        val viewModel =
                                            hiltViewModel<TicketListViewModel, TicketListViewModel.Factory>(
                                                creationCallback = { factory ->
                                                    factory.create(key)
                                                })
                                        TicketListScreen(viewModel = viewModel, onClick = { data ->
                                            backStack.add(TicketDetail(data))
                                        })
                                    }

                                    is TicketDetail -> NavEntry(key) {
                                        bottomBarState.value = false
                                        val viewModel =
                                            hiltViewModel<TicketDetailViewModel, TicketDetailViewModel.Factory>(
                                                creationCallback = { factory ->
                                                    factory.create(key)
                                                })
                                        TicketDetailScreen(viewModel = viewModel)
                                    }

                                    is Search -> NavEntry(key) {
                                        bottomBarState.value = true
                                        SearchScreen()
                                    }

                                    is Settings -> NavEntry(key) {
                                        bottomBarState.value = true
                                        SettingsScreen()
                                    }

                                    else -> NavEntry(Unit) { Text("Unknown route") }
                                }
                            })
                    }
                }
            }
        }
    }
}
