package com.example.moodcalendar.Exception;

import com.example.moodcalendar.dto.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 요청
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    /**
     * 중복값 관련 예외 처리
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(DuplicateException e) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(e.getMessage(), HttpStatus.CONFLICT));
    }

    /**
     * 잘못된 요청, 필수 값 누락, 조건 미충족
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationError(MethodArgumentNotValidException e) {
        // 첫번째 유효성 검증 실패 메세지
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(message, HttpStatus.BAD_REQUEST));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception e) {
        log.error("Unexpected error: ", e); // Log
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
