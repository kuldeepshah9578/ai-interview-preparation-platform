package com.interview.ai_platform.dto;

public class WelcomeResponse {

    private String message;
    private String version;
    private String status;

    public WelcomeResponse(String message, String version, String status) {
        this.message = message;
        this.version = version;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }
}