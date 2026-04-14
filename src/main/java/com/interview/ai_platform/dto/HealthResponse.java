package com.interview.ai_platform.dto;

import java.time.LocalDateTime;

public class HealthResponse {

    private String status;
    private String service;
    private LocalDateTime timestamp;

    public HealthResponse(
            String status,
            String service,
            LocalDateTime timestamp) {

        this.status = status;
        this.service = service;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getService() {
        return service;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}