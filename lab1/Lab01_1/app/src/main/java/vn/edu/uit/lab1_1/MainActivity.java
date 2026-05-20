package vn.edu.uit.lab1_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize activity view and navigation buttons.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCodeLayout = findViewById(R.id.btn_code_layout);
        Button btnXmlLayout = findViewById(R.id.btn_xml_layout);

        btnCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to programmatic LinearLayout screen.
                startActivity(new Intent(MainActivity.this, CodeActivity.class));
            }
        });

        btnXmlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to XML LinearLayout screen.
                startActivity(new Intent(MainActivity.this, XmlActivity.class));
            }
        });
    }
}
