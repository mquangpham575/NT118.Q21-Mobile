package vn.edu.uit.lab2_5;

public class Dish {
    private String name;
    private Thumbnail thumbnail;
    private boolean hasPromotion;

    public Dish(String name, Thumbnail thumbnail, boolean hasPromotion) {
        // Initialize Dish instance.
        this.name = name;
        this.thumbnail = thumbnail;
        this.hasPromotion = hasPromotion;
    }

    public String getName() {
        // Return name.
        return name;
    }

    public void setName(String name) {
        // Set name.
        this.name = name;
    }

    public Thumbnail getThumbnail() {
        // Return thumbnail.
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        // Set thumbnail.
        this.thumbnail = thumbnail;
    }

    public boolean isHasPromotion() {
        // Return true if the dish is promoted.
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        // Set promotion status.
        this.hasPromotion = hasPromotion;
    }
}
