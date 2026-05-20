package vn.edu.uit.lab2_2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> names;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize ArrayList, Adapter, and bind button submit, click selection, and long-click deletion.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName = findViewById(R.id.et_name);
        Button btnSubmit = findViewById(R.id.btn_submit);
        TextView tvSelection = findViewById(R.id.tv_selection);
        ListView lvPerson = findViewById(R.id.lv_person);

        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                names
        );
        lvPerson.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add name from edit text to list and update layout interface.
                String nameInput = etName.getText().toString().trim();
                if (!nameInput.isEmpty()) {
                    names.add(nameInput);
                    etName.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show index and value of selected name on click.
                tvSelection.setText("vị trí: " + position + " , giá trị = " + names.get(position));
            }
        });

        lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Delete selected name from list on long click.
                names.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
