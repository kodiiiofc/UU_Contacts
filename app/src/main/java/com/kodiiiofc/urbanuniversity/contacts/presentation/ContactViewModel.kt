package com.kodiiiofc.urbanuniversity.contacts.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.contacts.data.ContactDatabase
import com.kodiiiofc.urbanuniversity.contacts.data.ContactRepositoryImpl
import com.kodiiiofc.urbanuniversity.contacts.domain.Contact
import com.kodiiiofc.urbanuniversity.contacts.domain.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository
    val contacts: LiveData<List<Contact>>

    init {
        val dao = ContactDatabase.getDatabase(application).getContactDao()
        repository = ContactRepositoryImpl(dao)
        contacts = repository.contacts
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }

}