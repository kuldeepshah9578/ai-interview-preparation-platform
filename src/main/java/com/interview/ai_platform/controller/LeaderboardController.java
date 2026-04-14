package com.interview.ai_platform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.ai_platform.dto.TopicLeaderboardResponse;
import com.interview.ai_platform.service.InterviewService;

@RestController
@RequestMapping("/api/v1/leaderboard")
public class LeaderboardController {

    private final InterviewService interviewService;

    public LeaderboardController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/topics")
    public List<TopicLeaderboardResponse> getTopicLeaderboard() {
        return interviewService.getTopicLeaderboard();
    }
}