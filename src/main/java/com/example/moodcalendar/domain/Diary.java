package com.example.moodcalendar.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary {

    private Long id;

    private Long userId;

    private Long emotionId;

    private String title;

    private String content; // 본문 

    private LocalDate diaryDate; // 일기 날짜

    private Boolean isPublic; // 공개 여부

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
