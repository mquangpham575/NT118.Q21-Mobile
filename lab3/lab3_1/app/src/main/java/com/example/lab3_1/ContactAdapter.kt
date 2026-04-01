package com.example.lab3_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(private val context: android.content.Context, private val contacts: List<Contact>) : ArrayAdapter<Contact>(context, R.layout.item_contact, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)

        val contact = contacts[position]

        view.findViewById<TextView>(R.id.tvId).text = "ID: ${contact.id}"
        view.findViewById<TextView>(R.id.tvName).text = contact.name
        view.findViewById<TextView>(R.id.tvPhone).text = contact.phone

        return view
    }
}
