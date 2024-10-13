package com.kodiiiofc.urbanuniversity.contacts.domain

interface ContactRepository {
    suspend fun addContact(contact: Contact)
    suspend fun loadDatabaseAsList() : List<Contact>
}