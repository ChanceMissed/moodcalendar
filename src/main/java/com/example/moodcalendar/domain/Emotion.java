package com.example.moodcalendar.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emotion {

    private Long id;

    private Long userId;   // null 가능 (기본 감정)

    private String emoji;

    private String name;

    private String color;

    private LocalDateTime createdAt;

}
