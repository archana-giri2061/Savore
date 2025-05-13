package com.Savore.model;

import java.time.LocalDateTime;

/**
 * Model class representing a user.
 */
public class UserModel {
    private Integer userId;
    private String username;
    private String email;
    private String password;
    private String address;
    private String role;
    private LocalDateTime createdAt;
    private Boolean isSubscribed;

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(Integer userId, String username, String email, String password, String address, 
                    String role, LocalDateTime createdAt, Boolean isSubscribed) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.isSubscribed = isSubscribed;
    }

    public UserModel(String username, String email, String password, String address, 
                    String role, LocalDateTime createdAt, Boolean isSubscribed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.isSubscribed = isSubscribed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
}