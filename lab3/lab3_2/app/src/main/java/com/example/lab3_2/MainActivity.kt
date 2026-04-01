package com.example.lab3_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvStudents: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var db: StudentDB
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rvStudents = findViewById(R.id.rvStudents)
        fabAdd = findViewById(R.id.fabAdd)
        db = StudentDB(this)

        rvStudents.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun loadStudents() {
        val students = db.getAllStudents()
        adapter = StudentAdapter(students)
        rvStudents.adapter = adapter

        adapter.setOnItemClickListener { student ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("student_id", student.id)
            startActivity(intent)
        }
    }
}
