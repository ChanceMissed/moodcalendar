package com.example.moodcalendar.dto.request;

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
public class DiaryRequestDto {

    private Long userId;

    private Long emotionId;

    private String content;

    private LocalDate diaryDate;

    private Boolean isPublic;

    public Diary toEntity(){
        return Diary.builder()
            .userId(this.userId)
            .emotionId(this.emotionId)
            .content(this.content)
            .diaryDate(this.diaryDate)
            .isPublic(this.isPublic)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public Diary toUpdateEntity(Long id) {
        return Diary.builder()
            .id(id)
            .userId(this.userId)
            .emotionId(this.emotionId)
            .content(this.content)
            .diaryDate(this.diaryDate)
            .isPublic(this.isPublic)
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
