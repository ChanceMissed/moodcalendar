package com.example.moodcalendar.dto.request;

import com.example.moodcalendar.domain.Diary;
import jakarta.validation.constraints.NotNull;
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
public class DiaryRequestDto {

    @NotNull(message = "유저 ID는 필수 입력값 입니다.")
    private Long userId;

    private Long emotionId;

    private String title;

    private String content;

    private LocalDate diaryDate;

    private Boolean isPublic;

    public Diary toEntity(){
        return Diary.builder()
            .userId(this.userId)
            .emotionId(this.emotionId)
            .title(this.title)
            .content(this.content)
            .diaryDate(this.diaryDate)
            .isPublic(this.isPublic)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public Diary toUpdateEntity(Long id, Long userId, LocalDateTime createdAt) {
        return Diary.builder()
            .id(id)
            .userId(userId)
            .emotionId(this.emotionId)
            .title(this.getTitle())
            .content(this.content)
            .diaryDate(this.diaryDate)
            .isPublic(this.isPublic)
            .createdAt(createdAt)         // 생성일도 유지
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
