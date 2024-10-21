package com.example.appcontatos.ui.contact.list

import com.example.appcontatos.data.Contact

data class ContactsListUiState(
    val isLoading : Boolean = false,
    val hasError : Boolean = false,
    val contacts: Map<String,List<Contact>> = mapOf(),
    val isInitialComposition : Boolean = false
){}