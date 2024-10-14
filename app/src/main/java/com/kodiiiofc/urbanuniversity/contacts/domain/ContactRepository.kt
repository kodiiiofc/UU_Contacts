package com.kodiiiofc.urbanuniversity.contacts.domain

import androidx.lifecycle.LiveData

interface ContactRepository {

    val contacts: LiveData<List<Contact>>

    suspend fun insert(contact: Contact)
    suspend fun delete(contact: Contact)
}