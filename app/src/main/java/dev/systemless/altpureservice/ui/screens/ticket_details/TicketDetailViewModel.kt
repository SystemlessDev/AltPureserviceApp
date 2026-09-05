package dev.systemless.altpureservice.ui.screens.ticket_details

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.systemless.altpureservice.repository.PureserviceRepository
import dev.systemless.altpureservice.ui.TicketDetail
import dev.systemless.altpureservice.ui.screens.ticket_list.TicketListViewModel

@HiltViewModel(assistedFactory = TicketDetailViewModel.Factory::class)
class TicketDetailViewModel @AssistedInject() constructor(
    val pureserviceRepository: PureserviceRepository,
    @Assisted val navKey: TicketDetail
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(navKey: TicketDetail): TicketDetailViewModel
    }
}
