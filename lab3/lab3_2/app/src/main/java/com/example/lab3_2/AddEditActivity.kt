package com.example.lab3_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddEditActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etMssv: TextInputEditText
    private lateinit var etLop: TextInputEditText
    private lateinit var etDiem: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var db: StudentDB

    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        etName = findViewById(R.id.etName)
        etMssv = findViewById(R.id.etMssv)
        etLop = findViewById(R.id.etLop)
        etDiem = findViewById(R.id.etDiem)
        btnSave = findViewById(R.id.btnSave)
        db = StudentDB(this)

        studentId = intent.getIntExtra("student_id", -1)

        if (studentId != -1) {
            val student = db.getStudentById(studentId)
            student?.let {
                etName.setText(it.name)
                etMssv.setText(it.mssv)
                etLop.setText(it.lop)
                etDiem.setText(it.diem.toString())
            }
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val mssv = etMssv.text.toString().trim()
            val lop = etLop.text.toString().trim()
            val diemStr = etDiem.text.toString().trim()

            if (name.isEmpty() || mssv.isEmpty() || lop.isEmpty() || diemStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val diem = diemStr.toFloatOrNull() ?: 0f

            if (studentId == -1) {
                db.insertStudent(Student(name = name, mssv = mssv, lop = lop, diem = diem))
            } else {
                db.updateStudent(Student(id = studentId, name = name, mssv = mssv, lop = lop, diem = diem))
            }

            Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
