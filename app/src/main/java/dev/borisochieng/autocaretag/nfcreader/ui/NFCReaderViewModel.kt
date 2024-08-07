package dev.borisochieng.autocaretag.nfcreader.ui

import android.content.Intent
import android.nfc.Tag
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.autocaretag.database.Client
import dev.borisochieng.autocaretag.database.repository.ClientRepository
import dev.borisochieng.autocaretag.nfcreader.data.State
import dev.borisochieng.autocaretag.nfcreader.repository.ClientId
import dev.borisochieng.autocaretag.nfcreader.repository.NFCReaderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NFCReaderViewModel : ViewModel(), KoinComponent {

    private val nfcReaderRepository: NFCReaderRepository by inject()
    private val clientRepository: ClientRepository by inject()

    var tag by mutableStateOf<Tag?>(null); private set

    private val _tagInfo = MutableStateFlow<State<ClientId>>(State.Loading)
    private val _clientUiState = MutableStateFlow(ClientUiState())
    val clientUiState: StateFlow<ClientUiState> get() = _clientUiState

    var nfcIsEnabledOnDevice by mutableStateOf(false); private set
    var tagIsEmpty by mutableStateOf(true); private set

    val nfcReadState = _tagInfo.asStateFlow()

    init {
        viewModelScope.launch {
            _tagInfo.collect { state ->
                if (state !is State.Success) return@collect
                fetchClientDetails(state.data)
            }
        }
//        viewModelScope.launch {
//            _clientUiState.collect { state ->
//                // Update the clientUiState value
//            }
//        }
        getClients()
    }

    fun readNFCTag(intent: Intent) {
        tagIsEmpty = true
        _tagInfo.value = State.Loading
        _tagInfo.value = nfcReaderRepository.readNFCTag(intent)
    }

    fun fetchClientDetails(clientId: String) {
        viewModelScope.launch {
            clientRepository.getClientById(clientId).collect { client ->
                tagIsEmpty = clientId !in clientUiState.value.clients.map { it.clientId }
                if (client == null) return@collect
                _clientUiState.update { it.copy(client = client) }
            }
        }
    }

    fun updateClientDetails(client: Client) {
        viewModelScope.launch {
            clientRepository.update(client)
        }
    }

    fun toggleNfcEnabledStatus(enabled: Boolean) {
        nfcIsEnabledOnDevice = enabled
    }

    private fun getClients() = viewModelScope.launch {
        clientRepository.getAllClients().collect { clientsFromDB ->
            _clientUiState.update { it.copy(clients = clientsFromDB) }
        }
    }

    fun onTagFetched(fetchedTag: Tag) {
        tag = fetchedTag
    }
}
