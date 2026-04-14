package com.interview.ai_platform.dto;

public class DashboardStatusResponse {

    private String service;
    private String status;

    public DashboardStatusResponse(String service, String status) {
        this.service = service;
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public String getStatus() {
        return status;
    }
}