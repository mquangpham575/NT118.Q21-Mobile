package vn.edu.uit.lab2_5;

public enum Thumbnail {
    Thumbnail1("Thumbnail 1", R.drawable.first_thumbnail),
    Thumbnail2("Thumbnail 2", R.drawable.second_thumbnail),
    Thumbnail3("Thumbnail 3", R.drawable.third_thumbnail),
    Thumbnail4("Thumbnail 4", R.drawable.fourth_thumbnail);

    private String name;
    private int img;

    Thumbnail(String name, int img) {
        // Initialize enum value with name and image resource id.
        this.name = name;
        this.img = img;
    }

    public String getName() {
        // Return name.
        return name;
    }

    public int getImg() {
        // Return image resource ID.
        return img;
    }
}
