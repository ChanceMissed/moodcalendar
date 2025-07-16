package com.example.moodcalendar.service;

import com.example.moodcalendar.Exception.BadRequestException;
import com.example.moodcalendar.Exception.NotFoundException;
import com.example.moodcalendar.domain.Diary;
import com.example.moodcalendar.domain.Emotion;
import com.example.moodcalendar.dto.request.DiaryRequestDto;
import com.example.moodcalendar.dto.request.DiarySearchRequest;
import com.example.moodcalendar.dto.response.DiaryResponseDto;
import com.example.moodcalendar.mapper.DiaryMapper;
import com.example.moodcalendar.mapper.EmotionMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DiaryService {

    private final DiaryMapper diaryMapper;

    private final EmotionMapper emotionMapper;

    public DiaryService(DiaryMapper diaryMapper, EmotionMapper emotionMapper) {
        this.diaryMapper = diaryMapper;
        this.emotionMapper = emotionMapper;
    }

    public DiaryResponseDto insertDiary(DiaryRequestDto diaryRequestDto) {
        log.info("일기 등록 요청 : {}", diaryRequestDto);

        // 일기 등록
        Diary diary = diaryRequestDto.toEntity();
        diaryMapper.insertDiary(diary);
        log.info("일기 등록 완료 : {}", diary);

        Long emotionId = diary.getEmotionId();

        if (emotionId != null) {
            Emotion emotion = validateExistsEmotion(emotionId);
            log.info("일기 등록 성공 (감정 포함): id={}, userId={}, title={}, content={}, date={}, emotionId={}, isPublic={}",
                    diary.getId(), diary.getUserId(), diary.getTitle(), diary.getContent(), diary.getDiaryDate(), diary.getEmotionId(),diary.getIsPublic());
            return DiaryResponseDto.from(diary, emotion.getName(), emotion.getEmoji());
        }

        log.info("일기 등록 성공 (감정 없음): id={}, userId={}, title={}, content={}, date={}, isPublic={}",
                diary.getId(), diary.getUserId(), diary.getTitle() ,diary.getContent(), diary.getDiaryDate(), diary.getIsPublic());
        return DiaryResponseDto.from(diary, "감정 정보 없음", "");
    }

    public void updateDiary(Long id, DiaryRequestDto diaryRequestDto) {
        log.info("일기 수정 요청 : {}", diaryRequestDto);

        Diary existingDiary = diaryMapper.selectDiaryById(id);
        if (existingDiary == null) {
            log.warn("수정할 일기를 찾을 수 없습니다.");
            throw new NotFoundException("수정할 일기를 찾을 수 없습니다.");
        }

        Diary diary = diaryRequestDto.toUpdateEntity(id, diaryRequestDto.getUserId(), existingDiary.getCreatedAt());
        diaryMapper.updateDiary(diary);
        log.info("일기 수정 완료 : {}", diary);
    }

    // 일기 단건 조회
    public DiaryResponseDto getDiary(Long id) {
        log.info("일기 조회 요청 : id={}", id);

        DiaryResponseDto diary = diaryMapper.selectDiaryWithEmotion(id);
        if (diary == null) {
            throw new NotFoundException("해당 일기를 찾을 수 없습니다.");
        }
        return diary;
    }

    // 유저별 일기 목록 조회
    public List<DiaryResponseDto> getDiariesByUserId(Long userId) {
        log.info("유저별 일기 목록 조회 요청: userId={}", userId);
        if (userId == null) {
            throw new BadRequestException("유저 ID는 필수입니다.");
        }

        List<DiaryResponseDto> diaries = diaryMapper.selectDiariesByUserId(userId);
        log.info("유저별 일기 목록 조회 완료: id = {} / {}개", userId, diaries.size());

        return diaries;
    }

    public void removeDiary(Long id) {
        log.info("일기 삭제 요청 : id={}", id);
        Diary diary = diaryMapper.selectDiaryById(id);

        if (diary == null) {
            log.warn("삭제할 일기를 찾을 수 없습니다. id={}", id);
            throw new NotFoundException("삭제할 일기를 찾을 수 없습니다.");
        }

        int deleted = diaryMapper.deleteDiaryById(id);
        if (deleted == 0) {
            log.warn("일기 삭제 실패 : id={}", id);
            throw new RuntimeException("삭제에 실패했습니다.");
        }
        log.info("일기 삭제 완료 : id={}", id);
    }

    public List<DiaryResponseDto> searchDiaries(Long userId, String title, Long emotionId, LocalDate fromDate, LocalDate toDate, String keyword) {
        log.info("일기 검색 요청: userId={}, title={}, emotionId={}, fromDate={}, toDate={}, keyword={}",
                userId, title, emotionId, fromDate, toDate, keyword);

        DiarySearchRequest searchRequest = new DiarySearchRequest(userId, title, emotionId, fromDate, toDate, keyword);
        List<DiaryResponseDto> diaries = diaryMapper.searchDiaries(searchRequest);

        if (diaries.isEmpty()) {
            log.info("검색 결과가 없습니다.");
        } else {
            log.info("검색 결과 조회 완료: {}건", diaries.size());
        }
        return diaries;
    }

    private Emotion validateExistsEmotion(Long id) {
        log.info("아이디로 감정 조회: {}", id);
        Emotion emotion = emotionMapper.selectEmotionById(id);

        if (emotion == null) {
            log.warn("해당 감정을 찾을 수 없습니다 아이디:{}", id);
            throw new NotFoundException("해당 감정을 찾을 수 없습니다.");
        }
        return emotion;
    }
}
