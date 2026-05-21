package vn.edu.uit.lab03_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHandler = new DatabaseHandler(this);

        final EditText etName = findViewById(R.id.et_detail_name);
        final EditText etStudentId = findViewById(R.id.et_detail_student_id);
        final EditText etClass = findViewById(R.id.et_detail_class);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnCancel = findViewById(R.id.btn_cancel);

        student = (Student) getIntent().getSerializableExtra("student");

        if (student != null) {
            etName.setText(student.getName());
            etStudentId.setText(student.getStudentId());
            etClass.setText(student.getClassName());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String studentId = etStudentId.getText().toString().trim();
                String className = etClass.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(studentId) || TextUtils.isEmpty(className)) {
                    Toast.makeText(DetailActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (student != null) {
                    student.setName(name);
                    student.setStudentId(studentId);
                    student.setClassName(className);
                    dbHandler.updateStudent(student);
                    Toast.makeText(DetailActivity.this, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
