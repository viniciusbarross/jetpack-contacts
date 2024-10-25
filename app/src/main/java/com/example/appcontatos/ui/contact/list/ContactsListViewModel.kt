package com.example.appcontatos.ui.contact.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcontatos.data.Contact
import com.example.appcontatos.data.ContactDatasource
import com.example.appcontatos.data.ContactsObserver
import com.example.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsListViewModel : ViewModel(), ContactsObserver{
    var uiState: ContactsListUiState by mutableStateOf(ContactsListUiState())
    private set

    init {
        ContactDatasource.instance.registerObserver(this)
        loadContacts()
    }

    override fun onCleared() {
        ContactDatasource.instance.unRegisterObserver(this)
        super.onCleared()
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
    }

    override fun onUpdate(updatedContacts: List<Contact>) {
        uiState = uiState.copy(contacts = updatedContacts.groupByInitial())
    }
}