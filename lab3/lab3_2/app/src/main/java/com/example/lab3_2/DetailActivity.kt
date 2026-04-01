package com.example.lab3_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class DetailActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvMssv: TextView
    private lateinit var tvLop: TextView
    private lateinit var tvDiem: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button
    private lateinit var toolbar: MaterialToolbar
    private lateinit var db: StudentDB

    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvName = findViewById(R.id.tvName)
        tvMssv = findViewById(R.id.tvMssv)
        tvLop = findViewById(R.id.tvLop)
        tvDiem = findViewById(R.id.tvDiem)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)
        toolbar = findViewById(R.id.toolbar)
        db = StudentDB(this)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        studentId = intent.getIntExtra("student_id", -1)

        if (studentId != -1) {
            val student = db.getStudentById(studentId)
            student?.let {
                tvName.text = it.name
                tvMssv.text = it.mssv
                tvLop.text = it.lop
                tvDiem.text = String.format("%.1f", it.diem)
            }
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("student_id", studentId)
            startActivity(intent)
            finish()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có muốn xóa sinh viên này?")
                .setPositiveButton("Xóa") { _, _ ->
                    db.deleteStudent(studentId)
                    Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        val student = db.getStudentById(studentId)
        student?.let {
            tvName.text = it.name
            tvMssv.text = it.mssv
            tvLop.text = it.lop
            tvDiem.text = String.format("%.1f", it.diem)
        }
    }
}
