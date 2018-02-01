package com.glaum.model;

public class NotificationResponse {

    private boolean success;

    public NotificationResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
