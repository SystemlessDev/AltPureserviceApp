package dev.systemless.altpureservice.ui.screens.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.systemless.altpureservice.ui.screens.login.LoginViewmodel

@Composable
fun SearchScreen(viewmodel: LoginViewmodel = hiltViewModel()) {
    Text("SearchScreen")
}
