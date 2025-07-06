package com.example.moodcalendar.dto.response;

import com.example.moodcalendar.domain.Emotion;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmotionResponseDto {
    private Long id;
    private Long userId;
    private String emoji;
    private String name;
    private String color;
    private LocalDateTime createdAt;

    public static EmotionResponseDto from(Emotion emotion) {
        return EmotionResponseDto.builder()
            .id(emotion.getId())
            .userId(emotion.getUserId())
            .emoji(emotion.getEmoji())
            .name(emotion.getName())
            .color(emotion.getColor())
            .createdAt(emotion.getCreatedAt())
            .build();
    }
}