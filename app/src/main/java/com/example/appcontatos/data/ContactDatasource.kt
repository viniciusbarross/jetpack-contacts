package com.example.appcontatos.data

import kotlin.random.Random

class ContactDatasource private constructor() {
    companion object{
        val instance:ContactDatasource by lazy {
            ContactDatasource()
        }
    }

    private val contacts: MutableList<Contact> = mutableListOf()

    init{
        contacts.addAll(generateContacts())
    }

    fun findAll(): List<Contact> = contacts.toList()

    fun findById(id: Int): Contact? = contacts.firstOrNull() { it.id == id }

    fun save(contact: Contact): Contact =
        if(contact.id > 0) {
            val index = contacts.indexOfFirst { it.id == contact.id }
            contact.also { contacts[index] = it }
        }
        else{
            val maxId: Int = contacts.maxBy { it.id }.id
            contact.copy(id = maxId + 1).also { contacts.add(it) }
        }

    fun delete(contact: Contact){
        if(contact.id > 0){
            contacts.removeIf{it.id ==  contact.id }
        }
    }
}


 fun generateContacts():List<Contact>{
    val firstNames = listOf("Joao","José","Everton","Marcos","Andre")
    val lastNames = listOf("Do carmo","Oliveira","Dos Santos","Da Silva","Brasil")

    val contacts: MutableList<Contact> = mutableListOf()
    for (i in 0..19) {
        var generatedNewContact = false
        while (!generatedNewContact) {

            val firsNameIndex = Random.nextInt(firstNames.size)
            val lastNameIndex = Random.nextInt(lastNames.size)
            val newContact = Contact(
                id = i + 1,
                firstName = firstNames[firsNameIndex],
                lastName = lastNames[lastNameIndex]
            )
            if (!contacts.any { it.fullName == newContact.fullName }) {
                contacts.add(newContact)
                generatedNewContact = true
            }
        }
    }
    return contacts
}