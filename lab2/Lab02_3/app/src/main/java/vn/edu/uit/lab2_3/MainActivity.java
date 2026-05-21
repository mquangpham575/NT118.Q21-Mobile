package vn.edu.uit.lab2_3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Employee> employees;
    private ArrayAdapter<Employee> adapter;
    private EditText etId;
    private EditText etName;
    private RadioGroup rgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        rgType = findViewById(R.id.rg_type);
        Button btnSubmit = findViewById(R.id.btn_submit);
        ListView lvEmployees = findViewById(R.id.lv_employees);

        employees = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                employees
        );
        lvEmployees.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                addNewEmployee();
            }
        });
    }

    public void addNewEmployee() {
        
        int checkedId = rgType.getCheckedRadioButtonId();
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if (id.isEmpty() || name.isEmpty()) {
            return;
        }

        Employee employee;
        if (checkedId == R.id.rd_chinhthuc) {
            employee = new EmployeeFullTime();
        } else {
            employee = new EmployeePartTime();
        }

        employee.setId(id);
        employee.setName(name);
        employees.add(employee);
        
        etId.setText("");
        etName.setText("");
        rgType.check(R.id.rd_chinhthuc);
        
        adapter.notifyDataSetChanged();
    }
}
