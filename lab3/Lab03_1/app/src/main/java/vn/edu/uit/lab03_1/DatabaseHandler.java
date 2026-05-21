package vn.edu.uit.lab03_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentsManager";
    private static final String TABLE_STUDENTS = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_CLASS = "class_name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_STUDENT_ID + " TEXT,"
                + KEY_CLASS + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_STUDENT_ID, student.getStudentId());
        values.put(KEY_CLASS, student.getClassName());

        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    
    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_STUDENT_ID, KEY_CLASS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Student student = new Student(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
        cursor.close();
        db.close();
        return student;
    }

    
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setStudentId(cursor.getString(2));
                student.setClassName(cursor.getString(3));
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_STUDENT_ID, student.getStudentId());
        values.put(KEY_CLASS, student.getClassName());

        int rows = db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
        return rows;
    }

    
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }
}
