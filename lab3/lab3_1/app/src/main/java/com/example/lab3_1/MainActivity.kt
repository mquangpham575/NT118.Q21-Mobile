package com.example.lab3_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var lvContacts: ListView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var db: ContactDB
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lvContacts = findViewById(R.id.lvContacts)
        fabAdd = findViewById(R.id.fabAdd)
        db = ContactDB(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }

        lvContacts.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)
            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("contact_id", contact?.id)
            startActivity(intent)
        }

        lvContacts.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)
            contact?.let {
                db.deleteContact(it.id)
                Toast.makeText(this, "Deleted: ${it.name}", Toast.LENGTH_SHORT).show()
                loadContacts()
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadContacts()
    }

    private fun loadContacts() {
        val contacts = db.getAllContacts()
        adapter = ContactAdapter(this, contacts)
        lvContacts.adapter = adapter
    }
}
