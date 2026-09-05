package dev.systemless.altpureservice.ui.screens.ticket_list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.systemless.altpureservice.data.remote.tickets.Ticket
import dev.systemless.altpureservice.repository.PureserviceRepository
import dev.systemless.altpureservice.ui.TicketList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

data class UiState(
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel(assistedFactory = TicketListViewModel.Factory::class)
class TicketListViewModel @AssistedInject constructor(
    val pureserviceRepository: PureserviceRepository,
    @Assisted val navKey: TicketList
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    @AssistedFactory
    interface Factory {
        fun create(navKey: TicketList): TicketListViewModel
    }

    init {
        viewModelScope.launch {
            var data = pureserviceRepository.getPureserviceTicketList(navKey.filter)
            _uiState.update {
                it.copy(
                    tickets = data,
                    isLoading = false
                )
            }
        }
    }


    fun test() {

    }
}
