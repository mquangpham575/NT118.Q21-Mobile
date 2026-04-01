package com.example.lab3_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEditActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button
    private lateinit var db: ContactDB

    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        btnSave = findViewById(R.id.btnSave)
        db = ContactDB(this)

        contactId = intent.getIntExtra("contact_id", -1)

        if (contactId != -1) {
            val contacts = db.getAllContacts()
            val contact = contacts.find { it.id == contactId }
            contact?.let {
                etName.setText(it.name)
                etPhone.setText(it.phone)
            }
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contactId == -1) {
                db.insertContact(Contact(name = name, phone = phone))
            } else {
                db.updateContact(Contact(id = contactId, name = name, phone = phone))
            }

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
