package com.example.moodcalendar.dto.request;

import com.example.moodcalendar.domain.Emotion;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO -> Entity 변환을 위한 클래스
 */
@Getter
@Setter
public class EmotionRequestDto {
    private Long userId;
    
    @NotBlank(message = "이모지는 필수 입력값 입니다.")
    private String emoji;
    
    @NotBlank(message = "이름은 필수 입력값 입니다.")
    private String name;

    @NotBlank(message = "색상은 필수 입력값 입니다.")
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
