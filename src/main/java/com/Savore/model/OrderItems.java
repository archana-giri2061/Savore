package com.Savore.model;

/**
 * Model class representing an order item in the system.
 * Links an order to a food item, along with quantity and price.
 * 
 * author: 23048573_ArchanaGiri
 */
public class OrderItems {

    private Integer orderItemId;
    private Integer orderId;
    private Integer foodId;
    private Integer quantity;
    private Double price;

    /**
     * Default constructor.
     */
    public OrderItems() {}

    /**
     * Constructor with only order item ID and order ID.
     */
    public OrderItems(Integer orderItemId, Integer orderId) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
    }

    /**
     * Full constructor including all fields.
     */
    public OrderItems(Integer orderItemId, Integer orderId, Integer foodId, Integer quantity, Double price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Constructor without order item ID (typically for inserts).
     */
    public OrderItems(Integer orderId, Integer foodId, Integer quantity, Double price) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    /** Gets the order item ID. */
    public Integer getOrderItemId() {
        return orderItemId;
    }

    /** Sets the order item ID. */
    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    /** Gets the order ID associated with this item. */
    public Integer getOrderId() {
        return orderId;
    }

    /** Sets the order ID associated with this item. */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /** Gets the food ID linked to this order item. */
    public Integer getFoodId() {
        return foodId;
    }

    /** Sets the food ID linked to this order item. */
    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    /** Gets the quantity of food items ordered. */
    public Integer getQuantity() {
        return quantity;
    }

    /** Sets the quantity of food items ordered. */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /** Gets the price per item or total for this item depending on design. */
    public Double getPrice() {
        return price;
    }

    /** Sets the price per item or total for this item depending on design. */
    public void setPrice(Double price) {
        this.price = price;
    }
}
