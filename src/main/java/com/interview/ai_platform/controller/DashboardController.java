package com.interview.ai_platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.ai_platform.dto.DashboardOverviewResponse;
import com.interview.ai_platform.dto.DashboardPerformanceResponse;
import com.interview.ai_platform.dto.DashboardStatusResponse;
import com.interview.ai_platform.service.InterviewService;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final InterviewService interviewService;

    public DashboardController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/overview")
    public DashboardOverviewResponse getOverview() {
        return interviewService.getDashboardOverview();
    }

    @GetMapping("/performance")
    public DashboardPerformanceResponse getPerformance() {
        return interviewService.getDashboardPerformance();
    }

    @GetMapping("/status")
    public DashboardStatusResponse getStatus() {
        return interviewService.getDashboardStatus();
    }
}