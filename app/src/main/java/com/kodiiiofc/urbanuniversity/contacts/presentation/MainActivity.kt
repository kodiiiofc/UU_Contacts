package com.kodiiiofc.urbanuniversity.contacts.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kodiiiofc.urbanuniversity.contacts.R
import com.kodiiiofc.urbanuniversity.contacts.data.ContactDatabase
import com.kodiiiofc.urbanuniversity.contacts.data.ContactRepositoryImpl
import com.kodiiiofc.urbanuniversity.contacts.domain.Contact
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var nameET: EditText
    private lateinit var phoneNumberET: EditText
    private lateinit var submitBtn: Button
    private lateinit var contactsRV: RecyclerView

    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        nameET = findViewById(R.id.et_name)
        phoneNumberET = findViewById(R.id.et_phone_number)
        submitBtn = findViewById(R.id.btn_submit)
        contactsRV = findViewById(R.id.rv_contacts)

        val contactDeleter = object : ContactAdapter.OnDeleteIconClickListener {
            override fun onDeleteIconClick(contact: Contact) {
               AlertDialog.Builder(this@MainActivity)
                   .setTitle("Внимание")
                   .setMessage("Вы собираетесь удалить ${contact.name}")
                   .setPositiveButton("Удалить") { _, _ ->
                       viewModel.deleteContact(contact)
                   }
                   .setNeutralButton("Отмена", null)
                   .create().show()
            }
        }

        val adapter = ContactAdapter(this, contactDeleter)
        contactsRV.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ContactViewModel::class.java]

        viewModel.contacts.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        submitBtn.setOnClickListener {
            val contactName = nameET.text.toString().trim()
            val phoneNumber = phoneNumberET.text.toString().trim()
            val timestamp = formatDate(Date())
            if (contactName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this@MainActivity, "Хотя бы одно из полей не заполнено. Данные не были сохранены", Toast.LENGTH_LONG).show()
            } else {
                viewModel.insertContact(Contact(contactName, phoneNumber, timestamp))
                Toast.makeText(this@MainActivity, "Данные успешно сохранены", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: Date): String {
        val formater = SimpleDateFormat("EEE,  HH:mm")
        return formater.format(date)
    }
}