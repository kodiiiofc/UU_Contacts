package com.kodiiiofc.urbanuniversity.contacts.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kodiiiofc.urbanuniversity.contacts.R
import com.kodiiiofc.urbanuniversity.contacts.data.ContactDatabase
import com.kodiiiofc.urbanuniversity.contacts.data.ContactRepositoryImpl
import com.kodiiiofc.urbanuniversity.contacts.domain.Contact
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var nameET: EditText
    private lateinit var phoneNumberET: EditText
    private lateinit var submitBtn: Button
    private lateinit var outputTV: TextView

    var db: ContactDatabase? = null
    private val contactRepository by lazy { ContactRepositoryImpl(database = db!!) }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        nameET = findViewById(R.id.et_name)
        phoneNumberET = findViewById(R.id.et_phone_number)
        submitBtn = findViewById(R.id.btn_submit)
        outputTV = findViewById(R.id.tv_output)
        db = ContactDatabase.getDatabase(this)
        outputTV.setText("")
        GlobalScope.async{
            val list = contactRepository.loadDatabaseAsList()
            list.forEach{outputTV.append(it.toString() + "\n")}
        }

        submitBtn.setOnClickListener {
            outputTV.setText("")
            GlobalScope.async {
                contactRepository.addContact(Contact(nameET.text.toString(), phoneNumberET.text.toString()))
                val list = contactRepository.loadDatabaseAsList()
                list.forEach{outputTV.append(it.toString() + "\n")}
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
}