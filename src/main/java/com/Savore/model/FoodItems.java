package com.Savore.model;

/**
 * Model class representing a food item in the system.
 * Contains fields for ID, name, description, price, country, image URL, and availability.
 * 
 * author: 23048573_ArchanaGiri
 */
public class FoodItems {

    private Integer foodId;
    private String foodName;
    private String description;
    private Double price;
    private String country;
    private String imageUrl;
    private String availability;

    /**
     * Default constructor.
     */
    public FoodItems() {}

    /**
     * Constructor for name and description only.
     */
    public FoodItems(String foodName, String description) {
        this.foodName = foodName;
        this.description = description;
    }

    /**
     * Full constructor with ID (used for updates and retrieval).
     */
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

    /**
     * Constructor without ID (used for creation).
     */
    public FoodItems(String foodName, String description, Double price, String country, 
                     String imageUrl, String availability) {
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.country = country;
        this.imageUrl = imageUrl;
        this.availability = availability;
    }

    // Getters and Setters

    /** Gets the food ID. */
    public Integer getFoodId() {
        return foodId;
    }

    /** Sets the food ID. */
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    /** Gets the food name. */
    public String getFoodName() {
        return foodName;
    }

    /** Sets the food name. */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /** Gets the food description. */
    public String getDescription() {
        return description;
    }

    /** Sets the food description. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Gets the price of the food item. */
    public Double getPrice() {
        return price;
    }

    /** Sets the price of the food item. */
    public void setPrice(Double price) {
        this.price = price;
    }

    /** Gets the country of origin for the food item. */
    public String getCountry() {
        return country;
    }

    /** Sets the country of origin for the food item. */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Gets the image URL for the food item. */
    public String getImageUrl() {
        return imageUrl;
    }

    /** Sets the image URL for the food item. */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** Gets the availability status of the food item. */
    public String getAvailability() {
        return availability;
    }

    /** Sets the availability status of the food item. */
    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
