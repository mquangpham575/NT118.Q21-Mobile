package com.example.lab3_2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class StudentDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentManager.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENTS = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_MSSV = "mssv"
        private const val COLUMN_LOP = "lop"
        private const val COLUMN_DIEM = "diem"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_STUDENTS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_MSSV TEXT, $COLUMN_LOP TEXT, $COLUMN_DIEM REAL)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    fun insertStudent(student: Student): Long {
        Log.d("StudentDB", "inserting")
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_MSSV, student.mssv)
            put(COLUMN_LOP, student.lop)
            put(COLUMN_DIEM, student.diem)
        }
        return db.insert(TABLE_STUDENTS, null, values)
    }

    fun getAllStudents(): List<Student> {
        Log.d("StudentDB", "reading all students")
        val students = mutableListOf<Student>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_STUDENTS, null, null, null, null, null, "$COLUMN_NAME ASC")

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val mssv = getString(getColumnIndexOrThrow(COLUMN_MSSV))
                val lop = getString(getColumnIndexOrThrow(COLUMN_LOP))
                val diem = getFloat(getColumnIndexOrThrow(COLUMN_DIEM))
                Log.d("StudentDB", "id: $id, name: $name, mssv: $mssv, lop: $lop, diem: $diem")
                students.add(Student(id, name, mssv, lop, diem))
            }
            close()
        }
        return students
    }

    fun getStudentById(id: Int): Student? {
        val db = readableDatabase
        val cursor = db.query(TABLE_STUDENTS, null, "$COLUMN_ID = ?", arrayOf(id.toString()), null, null, null)
        
        return with(cursor) {
            if (moveToFirst()) {
                val student = Student(
                    getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(COLUMN_MSSV)),
                    getString(getColumnIndexOrThrow(COLUMN_LOP)),
                    getFloat(getColumnIndexOrThrow(COLUMN_DIEM))
                )
                close()
                student
            } else {
                close()
                null
            }
        }
    }

    fun updateStudent(student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_MSSV, student.mssv)
            put(COLUMN_LOP, student.lop)
            put(COLUMN_DIEM, student.diem)
        }
        return db.update(TABLE_STUDENTS, values, "$COLUMN_ID = ?", arrayOf(student.id.toString()))
    }

    fun deleteStudent(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_STUDENTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
