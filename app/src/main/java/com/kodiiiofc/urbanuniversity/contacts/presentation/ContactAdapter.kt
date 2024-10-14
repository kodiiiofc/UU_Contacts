package com.kodiiiofc.urbanuniversity.contacts.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kodiiiofc.urbanuniversity.contacts.R
import com.kodiiiofc.urbanuniversity.contacts.domain.Contact

class ContactAdapter(private val context: Context, private val iconClickListener: OnDeleteIconClickListener) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    val contacts = mutableListOf<Contact>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Contact>) {
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTV : TextView = itemView.findViewById(R.id.tv_name)
        private val phoneNumberTV: TextView = itemView.findViewById(R.id.tv_phone_number)
        private val timestampTV: TextView = itemView.findViewById(R.id.tv_timestamp)
        val deleteIV: ImageView = itemView.findViewById(R.id.iv_delete)

        fun bind(contact: Contact) {
            nameTV.text = contact.name
            phoneNumberTV.text = contact.phoneNumber
            timestampTV.text = contact.timestamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val viewHolder = ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
        viewHolder.deleteIV.setOnClickListener {
            iconClickListener.onDeleteIconClick(contacts[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contacts[position]
        holder.bind(currentContact)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    interface OnDeleteIconClickListener {
        fun onDeleteIconClick(contact: Contact)
    }

}