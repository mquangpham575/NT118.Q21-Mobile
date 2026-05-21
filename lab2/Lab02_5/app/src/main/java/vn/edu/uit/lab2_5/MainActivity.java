package vn.edu.uit.lab2_5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Dish> dishes;
    private DishAdapter dishAdapter;
    private EditText etDishName;
    private Spinner spThumbnail;
    private CheckBox cbPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDishName = findViewById(R.id.et_dish_name);
        spThumbnail = findViewById(R.id.sp_thumbnail);
        cbPromotion = findViewById(R.id.cb_promotion);
        Button btnAddDish = findViewById(R.id.btn_add_dish);
        GridView gvDishes = findViewById(R.id.gv_dishes);

        
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(
                this,
                R.layout.item_selected_thumbnail,
                Arrays.asList(Thumbnail.values())
        );
        spThumbnail.setAdapter(thumbnailAdapter);

        
        dishes = new ArrayList<>();
        dishAdapter = new DishAdapter(this, R.layout.item_dish, dishes);
        gvDishes.setAdapter(dishAdapter);

        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String name = etDishName.getText().toString().trim();
                if (name.isEmpty()) {
                    return;
                }

                Thumbnail selectedThumbnail = (Thumbnail) spThumbnail.getSelectedItem();
                boolean isPromo = cbPromotion.isChecked();

                Dish dish = new Dish(name, selectedThumbnail, isPromo);
                dishes.add(dish);

                etDishName.setText("");
                spThumbnail.setSelection(0);
                cbPromotion.setChecked(false);

                dishAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
