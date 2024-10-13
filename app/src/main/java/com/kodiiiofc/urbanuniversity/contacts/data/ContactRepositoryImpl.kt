package com.kodiiiofc.urbanuniversity.contacts.data

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kodiiiofc.urbanuniversity.contacts.R
import com.kodiiiofc.urbanuniversity.contacts.domain.Contact
import com.kodiiiofc.urbanuniversity.contacts.domain.ContactRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ContactRepositoryImpl(val database: ContactDatabase) : ContactRepository {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun addContact(contact: Contact) = GlobalScope.async {
        database.getContactDao().insert(contact)
    }.await()

    override suspend fun loadDatabaseAsList() : List<Contact> = GlobalScope.async {
        database.getContactDao().getAllContacts()
    }.await()
}