package com.example.moodcalendar.mapper;

import com.example.moodcalendar.domain.Diary;
import com.example.moodcalendar.dto.response.DiaryResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryMapper {
    // 1. 일기 단건 조회
    Diary selectDiaryById(Long id);

    // 2. 유저별 일기 목록 조회 (페이징, 공개여부 조건 포함 가능)
    List<Diary> selectDiariesByUserId(Long userId);

    // 3. 일기 등록
    int insertDiary(Diary diary);

    // 4. 일기 수정 (부분 업데이트는 XML에서 동적 쿼리)
    int updateDiary(Diary diary);

    // 5. 일기 삭제
    int deleteDiaryById(Long id);

    //6. 조인 응답
    DiaryResponseDto selectDiaryWithEmotion(Long id);

}
