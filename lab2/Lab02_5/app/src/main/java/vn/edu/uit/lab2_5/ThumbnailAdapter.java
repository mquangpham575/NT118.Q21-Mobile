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

public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {

    public ThumbnailAdapter(@NonNull Context context, int resource, @NonNull List<Thumbnail> objects) {
        // Initialize spinner adapter.
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Set layout for selected item in spinner (only showing name).
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_thumbnail, parent, false);
        }

        Thumbnail thumbnail = getItem(position);
        TextView tvName = convertView.findViewById(R.id.tv_selected_thumb_name);
        if (thumbnail != null) {
            tvName.setText(thumbnail.getName());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Set layout for dropdown list items (showing name and image).
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_thumbnail, parent, false);
        }

        Thumbnail thumbnail = getItem(position);
        TextView tvName = convertView.findViewById(R.id.tv_thumb_name);
        ImageView ivImg = convertView.findViewById(R.id.iv_thumb_img);

        if (thumbnail != null) {
            tvName.setText(thumbnail.getName());
            ivImg.setImageResource(thumbnail.getImg());
        }

        return convertView;
    }
}
