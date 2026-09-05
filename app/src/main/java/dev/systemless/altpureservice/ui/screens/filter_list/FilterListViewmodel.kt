package dev.systemless.altpureservice.ui.screens.filter_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.systemless.altpureservice.data.remote.tickets.PureserviceList
import dev.systemless.altpureservice.repository.PureserviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val lists: List<PureserviceList> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class FilterListViewmodel @Inject constructor(
    val pureserviceRepository: PureserviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            refreshFilterList()
        }
    }

    suspend fun refreshFilterList() {
        val data = pureserviceRepository.getPureserviceLists()
        _uiState.update {
            it.copy(
                lists = data,
                isLoading = false
            )
        }
    }
}
