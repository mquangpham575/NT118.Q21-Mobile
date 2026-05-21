package vn.edu.uit.lab2_4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Employee> employees;
    private EmployeeAdapter adapter;
    private EditText etId;
    private EditText etFullName;
    private CheckBox cbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.et_id);
        etFullName = findViewById(R.id.et_fullname);
        cbManager = findViewById(R.id.cb_manager);
        Button btnAdd = findViewById(R.id.btn_add);
        ListView lvEmployees = findViewById(R.id.lv_employees);

        employees = new ArrayList<>();
        adapter = new EmployeeAdapter(this, R.layout.item_employee, employees);
        lvEmployees.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String id = etId.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                boolean isManager = cbManager.isChecked();

                if (!id.isEmpty() && !fullName.isEmpty()) {
                    Employee employee = new Employee(id, fullName, isManager);
                    employees.add(employee);

                    etId.setText("");
                    etFullName.setText("");
                    cbManager.setChecked(false);

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
