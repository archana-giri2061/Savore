package com.Savore.model;

import java.time.LocalDateTime;
import java.sql.Timestamp;

/**
 * Model class representing an order.
 */
public class OrderModel {
    private Integer orderId;
    private Integer userId;
    private String userName; // Added field
    private String foodItems; // Added field
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private String deliveryAddress;

    public OrderModel() {
    }

    public OrderModel(Integer orderId, Integer userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public OrderModel(Integer orderId, Integer userId, LocalDateTime orderDate, String status, 
                     Double totalAmount, String deliveryAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
    }

    public OrderModel(Integer userId, LocalDateTime orderDate, String status, Double totalAmount, 
                     String deliveryAddress) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderDate(Timestamp timestamp) {
        if (timestamp != null) {
            this.orderDate = timestamp.toLocalDateTime();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}