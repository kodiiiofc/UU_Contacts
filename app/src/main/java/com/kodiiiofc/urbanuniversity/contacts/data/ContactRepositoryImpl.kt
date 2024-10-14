package com.kodiiiofc.urbanuniversity.contacts.data

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.kodiiiofc.urbanuniversity.contacts.R
import com.kodiiiofc.urbanuniversity.contacts.domain.Contact
import com.kodiiiofc.urbanuniversity.contacts.domain.ContactRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ContactRepositoryImpl(private val contactDao: ContactDao) : ContactRepository {

    override val contacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    override suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    override suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }

}