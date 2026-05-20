package vn.edu.uit.lab2_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {

    public DishAdapter(@NonNull Context context, int resource, @NonNull List<Dish> objects) {
        // Initialize grid adapter.
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate grid item view, populate dish info, and set marquee selection.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dish, parent, false);
        }

        Dish dish = getItem(position);
        ImageView ivDish = convertView.findViewById(R.id.iv_dish_img);
        ImageView ivStar = convertView.findViewById(R.id.iv_star_promo);
        TextView tvName = convertView.findViewById(R.id.tv_dish_name);

        if (dish != null) {
            ivDish.setImageResource(dish.getThumbnail().getImg());
            tvName.setText(dish.getName());
            tvName.setSelected(true); // Needed for marquee effect to scroll horizontally

            if (dish.isHasPromotion()) {
                ivStar.setVisibility(View.VISIBLE);
            } else {
                ivStar.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
