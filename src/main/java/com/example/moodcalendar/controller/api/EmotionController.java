package com.example.moodcalendar.controller.api;

import com.example.moodcalendar.dto.common.ApiResponse;
import com.example.moodcalendar.dto.request.EmotionRequestDto;
import com.example.moodcalendar.dto.response.EmotionResponseDto;
import com.example.moodcalendar.service.EmotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Emotion API", description="감정 관련 API")
@Slf4j
@RequestMapping("/api/emotions")
@RestController
public class EmotionController {

    private final EmotionService emotionService;


    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    /**
     * {
     *   "success": true,
     *   "message": "감정 목록 조회 성공",
     *   "data": [
     *     {
     *       "id": 1,
     *       "name": "기쁨",
     *       "emoji": "😊",
     *       "color": "#F9D342"
     *     }
     *   ]
     * }
     */
    /**
     * 감정 등록
     * @param emotionRequestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EmotionResponseDto>> addEmotion(
        @Valid @RequestBody EmotionRequestDto emotionRequestDto) {

        log.info("감정 등록 요청: {}", emotionRequestDto);
        EmotionResponseDto emotionResponseDto = emotionService.addEmotion(emotionRequestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(emotionResponseDto, " 감정 등록이 완료되었습니다."));
    }

    /**
     * 감정 전체 조회
     * @return List<EmotionResponseDto>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmotionResponseDto>>> getAllEmotions(){
        log.info("모든 감정 조회");
        List<EmotionResponseDto> emotions = emotionService.getAllEmotions();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(emotions, "모든 감정 조회 성공"));
    }

    /**
     * 감정 단건 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmotionResponseDto>> getEmotion(@PathVariable Long id) {
        log.info("감정 목록 조회 요청 : id={}", id);
        EmotionResponseDto emotion = emotionService.findEmotionById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(emotion, "감정 조회 성공"));
    }

    /**
     * 감정 수정
     * @param id
     * @param emotionRequestDto
     * @return
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Void>> updateEmotion(@PathVariable Long id,
        @Valid @RequestBody EmotionRequestDto emotionRequestDto
    ) {
        log.info("감정 수정 요청: id={}, {}", id, emotionRequestDto);
        emotionService.updateEmotion(id, emotionRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(null ,"감정 수정이 완료되었습니다."));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmotion(@PathVariable Long id){
        log.info("감정 삭제 요청: id={}", id);
        emotionService.deleteEmotionById(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(null ,"감정 삭제 완료되었습니다."));
    }

}
