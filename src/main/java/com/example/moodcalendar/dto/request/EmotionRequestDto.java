package com.example.moodcalendar.dto.request;

import com.example.moodcalendar.domain.Emotion;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmotionRequestDto {
    private Long userId;
    private String emoji;
    private String name;
    private String color;

    public Emotion toEntity() {
        return Emotion.builder()
            .userId(userId)
            .emoji(emoji)
            .name(name)
            .color(color)
            .build();
    }

    public Emotion toUpdateEntity(Long id) {
        return Emotion.builder()
            .id(id)
            .emoji(emoji)
            .name(name)
            .color(color)
            .build();
    }
}
