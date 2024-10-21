package com.example.appcontatos.ui.contact.details

import com.example.appcontatos.data.Contact

data class ContactDetailsUiState(
    val isLoading :Boolean = false,
    val hasErrorLoading: Boolean = false,
    val contact: Contact = Contact(),
    val showConfirmationDialog: Boolean = false,
    val isDeleting : Boolean = false,
    val hasErrorDeleting: Boolean = false
    )