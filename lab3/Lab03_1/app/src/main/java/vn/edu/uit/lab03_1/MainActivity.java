package vn.edu.uit.lab03_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    private static final int REQUEST_DETAIL = 101;

    private DatabaseHandler dbHandler;
    private StudentAdapter adapter;
    private List<Student> studentList = new ArrayList<>();

    private EditText etName;
    private EditText etStudentId;
    private EditText etClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);

        etName = findViewById(R.id.et_name);
        etStudentId = findViewById(R.id.et_student_id);
        etClass = findViewById(R.id.et_class);
        Button btnAdd = findViewById(R.id.btn_add);
        RecyclerView rvStudents = findViewById(R.id.rv_students);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentList, this);
        rvStudents.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        loadStudents();
    }

    private void loadStudents() {
        studentList = dbHandler.getAllStudents();
        adapter.updateData(studentList);
    }

    private void addStudent() {
        String name = etName.getText().toString().trim();
        String studentId = etStudentId.getText().toString().trim();
        String className = etClass.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(studentId) || TextUtils.isEmpty(className)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(name, studentId, className);
        dbHandler.addStudent(student);
        Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();

        etName.setText("");
        etStudentId.setText("");
        etClass.setText("");

        loadStudents();
    }

    @Override
    public void onItemClick(Student student) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("student", student);
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    @Override
    public void onItemLongClick(final Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sinh viên")
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên " + student.getName() + " không?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteStudent(student);
                        Toast.makeText(MainActivity.this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show();
                        loadStudents();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            loadStudents();
        }
    }
}
