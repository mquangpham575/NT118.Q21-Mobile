package vn.edu.uit.lab2_5;

public class Dish {
    private String name;
    private Thumbnail thumbnail;
    private boolean hasPromotion;

    public Dish(String name, Thumbnail thumbnail, boolean hasPromotion) {
        
        this.name = name;
        this.thumbnail = thumbnail;
        this.hasPromotion = hasPromotion;
    }

    public String getName() {
        
        return name;
    }

    public void setName(String name) {
        
        this.name = name;
    }

    public Thumbnail getThumbnail() {
        
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        
        this.thumbnail = thumbnail;
    }

    public boolean isHasPromotion() {
        
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        
        this.hasPromotion = hasPromotion;
    }
}
