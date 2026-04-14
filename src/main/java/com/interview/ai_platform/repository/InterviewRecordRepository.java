package com.interview.ai_platform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.ai_platform.model.InterviewRecord;

public interface InterviewRecordRepository extends JpaRepository<InterviewRecord, Long> {

    long countByCategoryIgnoreCase(String category);

    List<InterviewRecord> findByScoreBetween(int min, int max);

    List<InterviewRecord> findAllByOrderByCreatedAtDesc();

    List<InterviewRecord> findByCategoryIgnoreCase(String category);

    List<InterviewRecord> findByDifficultyIgnoreCase(String difficulty);

    List<InterviewRecord> findByQuestionContainingIgnoreCase(String keyword);

    List<InterviewRecord> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    long countByScoreGreaterThanEqual(int score);

    long countByScoreLessThanEqual(int score);

    Optional<InterviewRecord> findFirstByOrderByCreatedAtDesc();

    Optional<InterviewRecord> findFirstByOrderByCreatedAtAsc();

    long countByDifficultyIgnoreCase(String difficulty);
}