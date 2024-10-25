package com.example.appcontatos.data

interface ContactsObserver {
    fun onUpdate(updatedContacts : List<Contact>)


}