package com.example.moodcalendar.controller;

import com.example.moodcalendar.dto.common.ApiResponse;
import com.example.moodcalendar.dto.request.DiaryRequestDto;
import com.example.moodcalendar.dto.response.DiaryResponseDto;
import com.example.moodcalendar.service.DiaryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/diaries")
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    /**
     * 일기 등록 API
     * @param diaryRequestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DiaryResponseDto>> addDiary(
        @Valid @RequestBody DiaryRequestDto diaryRequestDto) {
        log.info("일기 등록 요청 : {}", diaryRequestDto);

        DiaryResponseDto diaryResponseDto = diaryService.insertDiary(diaryRequestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(diaryResponseDto, "일기 등록이 완료 되었습니다."));
    }

    /**
     * 일기 단건 조회 API
      * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiaryResponseDto>> getDiary(@PathVariable Long id) {
        log.info("일기 검색(한건) 요청 : {}", id);

        DiaryResponseDto diaryResponseDto = diaryService.getDiary(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(diaryResponseDto, "일기 한건 조회 완료"));
    }

    /**
     * 유저별 일기 목록 전체 조회 API
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<DiaryResponseDto>>> getDiariesByUserId(@PathVariable Long userId) {
        log.info("유저별 일기 목록 조회 요청: userId={}", userId);
        List<DiaryResponseDto> diaryResponseDtoList = diaryService.getDiariesByUserId(userId);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(diaryResponseDtoList, "유저별 일기 목록 조회 완료"));
    }


    /**
     * 일기 수정 API
     * @param userId
     * @param diaryRequestDto
     * @return
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Void>> updateDiary(@PathVariable Long id,
        @Valid @RequestBody DiaryRequestDto diaryRequestDto) {
        log.info("유저별 일기 목록 조회 요청: id={}, {}", id, diaryRequestDto);
        diaryService.updateDiary(id, diaryRequestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(null, "일기 수정 완료"));
    }

    /**
     * 일기 삭제 API
     * @param userId
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDiary(@PathVariable Long id) {
        log.info("일기 삭제 요청 : id={}", id);
        diaryService.removeDiary(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(null, "일기 삭제 요청"));
    }
}
