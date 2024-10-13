package com.kodiiiofc.urbanuniversity.contacts.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("phone_number") var phoneNumber: String = "empty") {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}