package com.Savore.model;

import java.time.LocalDateTime;

/**
 * Model class representing a user in the system.
 * Contains user identity, credentials, contact info, role, image, and subscription status.
 * 
 * author: 23048573_ArchanaGiri
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
    private String image_URL;

    /** Default constructor. */
    public UserModel() {}

    /** Constructor for login purposes (username + password only). */
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /** Full constructor with ID (used for fetching or updating users). */
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

    /** Constructor without ID (used for registration). */
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

    /** Gets the user ID. */
    public Integer getUserId() {
        return userId;
    }

    /** Sets the user ID. */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** Gets the username. */
    public String getUsername() {
        return username;
    }

    /** Sets the username. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Gets the user's email address. */
    public String getEmail() {
        return email;
    }

    /** Sets the user's email address. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Gets the encrypted password. */
    public String getPassword() {
        return password;
    }

    /** Sets the encrypted password. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Gets the user's physical address. */
    public String getAddress() {
        return address;
    }

    /** Sets the user's physical address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Gets the role of the user (e.g., ADMIN, USER). */
    public String getRole() {
        return role;
    }

    /** Sets the user's role (e.g., ADMIN, USER). */
    public void setRole(String role) {
        this.role = role;
    }

    /** Gets the account creation timestamp. */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** Sets the account creation timestamp. */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** Gets the subscription status (true if subscribed). */
    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    /** Sets the subscription status. */
    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    /** Gets the filename of the user's profile image. */
    public String getImage_URL() {
        return image_URL;
    }

    /** Sets the filename of the user's profile image. */
    public void setImage_URL(String image_URL) {
        this.image_URL = image_URL;
    }
}
