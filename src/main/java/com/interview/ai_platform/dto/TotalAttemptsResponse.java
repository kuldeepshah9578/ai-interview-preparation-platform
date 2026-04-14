package com.interview.ai_platform.dto;

public class TotalAttemptsResponse {

    private long totalAttempts;

    public TotalAttemptsResponse(long totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public long getTotalAttempts() {
        return totalAttempts;
    }
}