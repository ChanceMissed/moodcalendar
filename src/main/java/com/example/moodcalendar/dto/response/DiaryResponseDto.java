package com.example.moodcalendar.dto.response;

import com.example.moodcalendar.domain.Diary;
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
@NoArgsConstructor // 직렬화
@AllArgsConstructor // 빌더
public class DiaryResponseDto {
    private Long id;
    private Long userId;

    private Long emotionId; // 감정 ID
    private String emotionName; // 감정 이름 (ex. 기쁨, 슬픔 등) // Join
    private String emoji;      // 감정 이모지  (😢, 😊 등) // Join

    private String content;
    private LocalDate diaryDate;

    private Boolean isPublic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Entity -> DTO
     * @param diary
     * @param emotionName
     * @param emoji
     * @return
     */
    public static DiaryResponseDto from(Diary diary, String emotionName, String emoji) {
        return DiaryResponseDto.builder()
            .id(diary.getId())
            .userId(diary.getUserId())
            .emotionId(diary.getEmotionId())
            .emotionName(emotionName)
            .emoji(emoji)
            .content(diary.getContent())
            .diaryDate(diary.getDiaryDate())
            .isPublic(diary.getIsPublic())
            .createdAt(diary.getCreatedAt())
            .updatedAt(diary.getUpdatedAt())
            .build();
    }
}
