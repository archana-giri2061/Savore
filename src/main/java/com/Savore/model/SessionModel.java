package com.Savore.model;

import java.time.LocalDateTime;

/**
 * Model class representing a user session.
 */
public class SessionModel {
    private String sessionId;
    private Integer userId;
    private String deviceType;
    private LocalDateTime createdAt;

    public SessionModel() {
    }

    public SessionModel(String sessionId, Integer userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public SessionModel(String sessionId, Integer userId, String deviceType, LocalDateTime createdAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.deviceType = deviceType;
        this.createdAt = createdAt;
    }

    public SessionModel(Integer userId, String deviceType, LocalDateTime createdAt) {
        this.userId = userId;
        this.deviceType = deviceType;
        this.createdAt = createdAt;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}