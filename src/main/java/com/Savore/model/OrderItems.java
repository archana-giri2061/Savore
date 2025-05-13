package com.Savore.model;

/**
 * Model class representing an order item.
 */
public class OrderItems {
    private Integer orderItemId;
    private Integer orderId;
    private Integer foodId;
    private Integer quantity;
    private Double price;

    public OrderItems() {
    }

    public OrderItems(Integer orderItemId, Integer orderId) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
    }

    public OrderItems(Integer orderItemId, Integer orderId, Integer foodId, Integer quantity, Double price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItems(Integer orderId, Integer foodId, Integer quantity, Double price) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}