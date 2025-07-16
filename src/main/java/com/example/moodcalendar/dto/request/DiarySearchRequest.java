package com.example.moodcalendar.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiarySearchRequest {
    
    private Long userId;

    private String title;

    private Long emotionId;
    
    private LocalDate fromDate;
    
    private LocalDate toDate;
    
    private String keyword; // 검색 키워드 (제목 or 내용)

}
