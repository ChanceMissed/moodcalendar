package com.example.moodcalendar.controller.api;

import com.example.moodcalendar.dto.common.ApiResponse;
import com.example.moodcalendar.dto.request.DiaryRequestDto;
import com.example.moodcalendar.dto.response.DiaryResponseDto;
import com.example.moodcalendar.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Diary API", description="일기 관련 API")
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
    @Operation(
        summary = "일기 등록 API",
        description = "일기를 등록하는 API"
        +" 일기 등록시 User ID는 필수 입니다."
    )
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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DiaryResponseDto>>> searchDiaries(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Long emotionId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        @RequestParam(required = false) String keyword) {

        log.info("일기 검색 요청: userId={}, emotionId={}, fromDate={}, toDate={}, keyword={}",
            userId, emotionId, fromDate, toDate, keyword);
        List<DiaryResponseDto> diaryResponseDtoList = diaryService.searchDiaries(userId, emotionId, fromDate, toDate, keyword);

        if (diaryResponseDtoList.isEmpty()) {
            log.info("검색 결과가 없습니다.");
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(diaryResponseDtoList, "검색 결과가 없습니다."));
        }

        log.info("검색 결과 조회 완료: {}건", diaryResponseDtoList.size());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(diaryResponseDtoList, " 검색 결과 조회 완료"));
    }
}
