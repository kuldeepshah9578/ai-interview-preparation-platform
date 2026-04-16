package com.interview.ai_platform.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interview.ai_platform.dto.AskRequest;
import com.interview.ai_platform.dto.AskResponse;
import com.interview.ai_platform.dto.AverageScoreResponse;
import com.interview.ai_platform.dto.HealthResponse;
import com.interview.ai_platform.dto.RecommendationResponse;
import com.interview.ai_platform.dto.SummaryResponse;
import com.interview.ai_platform.dto.TopTopicResponse;
import com.interview.ai_platform.dto.TotalAttemptsResponse;
import com.interview.ai_platform.dto.TotalScoreResponse;
import com.interview.ai_platform.dto.WelcomeResponse;
import com.interview.ai_platform.model.InterviewRecord;
import com.interview.ai_platform.service.InterviewService;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final InterviewService interviewService;

    public HelloController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello API working 🚀";
    }

    @PostMapping("/ask")
    public AskResponse askQuestion(@jakarta.validation.Valid @RequestBody AskRequest request) {
        var result = interviewService.getAnswer(
                request.getQuestion(),
                request.getCategory(),
                request.getDifficulty()
        );
        return new AskResponse(result.getAnswer(), result.getScore(), result.getFeedback());
    }

    @GetMapping("/history")
    public List<InterviewRecord> getHistory() {
        return interviewService.getHistory();
    }
    @GetMapping("/recent-history")
    public List<InterviewRecord> getRecentHistory() {
        return interviewService.getRecentHistory();
    }
    @GetMapping("/analytics")
    public Map<String, Integer> getAnalytics() {
        return interviewService.getCategoryAnalytics();
    }
    @GetMapping("/weak-areas")
    public Map<String, Integer> getWeakAreas() {
        return interviewService.getWeakAreas();
    }
    @GetMapping("/recommendation")
    public RecommendationResponse getRecommendation() {
        return interviewService.getRecommendation();
    }
    @GetMapping("/summary")
    public SummaryResponse getSummary() {
        return interviewService.getSummary();
    }
    @DeleteMapping("/history")
    public String deleteHistory() {
        interviewService.deleteAllHistory();
        return "All interview history deleted successfully.";
    }
    @GetMapping("/history/category/{category}")
    public List<InterviewRecord> getHistoryByCategory(@PathVariable String category) {
        return interviewService.getHistoryByCategory(category);
    }
    @GetMapping("/history/difficulty/{difficulty}")
    public List<InterviewRecord> getHistoryByDifficulty(@PathVariable String difficulty) {
        return interviewService.getHistoryByDifficulty(difficulty);
    }
    @GetMapping("/top-topic")
    public TopTopicResponse getTopTopic() {
        return interviewService.getTopTopic();
    }
    @GetMapping("/difficulty-stats")
    public Map<String, Integer> getDifficultyStats() {
        return interviewService.getDifficultyStats();
    }
    @GetMapping("/history/search/{keyword}")
    public List<InterviewRecord> searchHistoryByKeyword(@PathVariable String keyword) {
        return interviewService.searchHistoryByKeyword(keyword);
    }
    @PutMapping("/history/{id}/difficulty/{difficulty}")
    public InterviewRecord updateDifficulty(@PathVariable Long id, @PathVariable String difficulty) {
        return interviewService.updateDifficulty(id, difficulty);
    }
    @DeleteMapping("/history/{id}")
    public String deleteHistoryById(@PathVariable Long id) {
        return interviewService.deleteHistoryById(id);
    }
    @GetMapping("/history/paged")
    public Page<InterviewRecord> getPagedHistory(
            @RequestParam int page,
            @RequestParam int size) {
        return interviewService.getPagedHistory(page, size);
    }
    @GetMapping("/history/sorted")
    public List<InterviewRecord> getSortedHistory(
            @RequestParam String field,
            @RequestParam String direction) {

        return interviewService.getSortedHistory(field, direction);
    }
    @GetMapping("/history/date-range")
    public List<InterviewRecord> getHistoryByDateRange(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return interviewService.getHistoryByDateRange(start, end);
    }
    @GetMapping("/history/export")
    public ResponseEntity<String> exportHistory() {

        String csv =
                interviewService.exportHistoryToCSV();

        return ResponseEntity
                .ok()
                .header(
                    "Content-Disposition",
                    "attachment; filename=history.csv"
                )
                .body(csv);
    }
    @GetMapping("/health")
    public HealthResponse health() {

        return new HealthResponse(
                "UP",
                "AI Interview Platform",
                LocalDateTime.now()
        );
    }
    @GetMapping("/total-score")
    public TotalScoreResponse getTotalScore() {
        return interviewService.getTotalScore();
    }
    @GetMapping("/average-score")
    public AverageScoreResponse getAverageScore() {
        return interviewService.getAverageScore();
    }
    @GetMapping("/total-attempts")
    public TotalAttemptsResponse getTotalAttempts() {
        return interviewService.getTotalAttempts();
    }
    @DeleteMapping("/reset")
    public String resetDatabase() {
        return interviewService.resetDatabase();
    }
    @GetMapping("/welcome")
    public WelcomeResponse welcome() {
        return new WelcomeResponse(
                "Welcome to AI Interview Preparation Platform",
                "v1",
                "Running"
        );
    }
}