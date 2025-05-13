package com.Savore.model;

/**
 * Model class representing a food item.
 */
public class FoodItems {
    private Integer foodId;
    private String foodName;
    private String description;
    private Double price;
    private String country;
    private String imageUrl;
    private String availability;

    public FoodItems() {
    }

    public FoodItems(String foodName, String description) {
        this.foodName = foodName;
        this.description = description;
    }

    public FoodItems(Integer foodId, String foodName, String description, Double price, 
                    String country, String imageUrl, String availability) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.country = country;
        this.imageUrl = imageUrl;
        this.availability = availability;
    }

    public FoodItems(String foodName, String description, Double price, String country, 
                    String imageUrl, String availability) {
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.country = country;
        this.imageUrl = imageUrl;
        this.availability = availability;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}