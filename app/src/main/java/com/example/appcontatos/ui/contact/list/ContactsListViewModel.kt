package com.example.appcontatos.ui.contact.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcontatos.data.Contact
import com.example.appcontatos.data.ContactDatasource
import com.example.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsListViewModel : ViewModel(){
    var uiState: ContactsListUiState by mutableStateOf(ContactsListUiState())
    private set

    init {
        loadContacts()
    }

    fun loadContacts ()  {
        uiState = uiState.copy(
            isLoading = true,
            hasError = false,
            isInitialComposition = false
        )

        viewModelScope.launch {
            delay(2000)
            val contacts : List<Contact> = ContactDatasource.instance.findAll();
            uiState = uiState.copy(
                contacts = contacts.groupByInitial(),
                isLoading = false
            )
        }
    }



    fun toggleFavorite(contact: Contact)  {
        val updatedContact = contact.copy(isFavorite = !contact.isFavorite)
        ContactDatasource.instance.save(updatedContact)
        val contacts : List<Contact> = ContactDatasource.instance.findAll();
        uiState = uiState.copy(
            contacts = contacts.groupByInitial()
        )
    }
}