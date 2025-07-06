package com.example.moodcalendar.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Diary {

    private Long id;

    private Long userId;

    private Long emotionId;

    private String content;

    private LocalDate diaryDate;

    private Boolean isPublic;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
