package com.Savore.model;

import java.time.LocalDateTime;
import java.sql.Timestamp;

/**
 * Model class representing an order in the system.
 * Includes order ID, user details, order date, food list summary, status, total amount, and delivery address.
 * 
 * author: 23048573_ArchanaGiri
 */
public class OrderModel {
    
    private Integer orderId;
    private Integer userId;
    private String userName;     // For display purposes (e.g., admin dashboard)
    private String foodItems;    // Comma-separated list of food items (optional for UI)
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private String deliveryAddress;

    /** Default constructor. */
    public OrderModel() {}

    /** Constructor with order ID and user ID only. */
    public OrderModel(Integer orderId, Integer userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    /** Full constructor with all fields. */
    public OrderModel(Integer orderId, Integer userId, LocalDateTime orderDate, String status, 
                      Double totalAmount, String deliveryAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
    }

    /** Constructor without orderId (for creating new orders). */
    public OrderModel(Integer userId, LocalDateTime orderDate, String status, Double totalAmount, 
                      String deliveryAddress) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
    }

    /** Gets the order ID. */
    public Integer getOrderId() {
        return orderId;
    }

    /** Sets the order ID. */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /** Gets the user ID associated with this order. */
    public Integer getUserId() {
        return userId;
    }

    /** Sets the user ID associated with this order. */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** Gets the username of the customer (used in UI/reporting). */
    public String getUserName() {
        return userName;
    }

    /** Sets the username of the customer. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Gets the food items ordered (used in recent orders view). */
    public String getFoodItems() {
        return foodItems;
    }

    /** Sets the food items ordered as a comma-separated string. */
    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    /** Gets the order date. */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /** Sets the order date using LocalDateTime. */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /** Sets the order date using SQL Timestamp. */
    public void setOrderDate(Timestamp timestamp) {
        if (timestamp != null) {
            this.orderDate = timestamp.toLocalDateTime();
        }
    }

    /** Gets the current order status. */
    public String getStatus() {
        return status;
    }

    /** Sets the current order status. */
    public void setStatus(String status) {
        this.status = status;
    }

    /** Gets the total amount for the order. */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /** Sets the total amount for the order. */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /** Gets the delivery address of the order. */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /** Sets the delivery address of the order. */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
