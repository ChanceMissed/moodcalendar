package com.example.moodcalendar.controller.api;

import com.example.moodcalendar.dto.common.ApiResponse;
import com.example.moodcalendar.dto.request.UserRequestDto;
import com.example.moodcalendar.dto.response.UserResponseDto;
import com.example.moodcalendar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description="유저 관련 API")
@Slf4j
@Service
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 유저 등록 API
     * 유저 등록후 조회해서 -> Client or Frontend 에 반환 
     * @param userRequestDto
     * @return
     */
    @Operation(
        summary = "유저 등록 API",
        description = "유저를 등록하는 API 입니다. "
            + "유저를 등록하고 바로 조회해서 응답합니다."
            + "유저 등록시 이메일과 닉네임은 필수"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@RequestBody UserRequestDto userRequestDto) {
        log.info("유저 등록 요청 : userRequestDto={}", userRequestDto);

        Long id = userService.registerUser(userRequestDto);
        // 등록 후 바로 조회해서 응답
        UserResponseDto userResponseDto = userService.findUserById(id);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(userResponseDto, "유저를 성공적으로 등록시켰습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable Long id) {
        log.info("유저 조회 요청 : userId={}", id);

        UserResponseDto userResponseDto = userService.findUserById(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(userResponseDto, "유저를 성공적으로 조회 시켰습니다"));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long id,
        @RequestBody UserRequestDto userRequestDto) {
        log.info("유저 업데이트 요청 : id={}, email={}, nickname={}", id, userRequestDto.getEmail(), userRequestDto.getNickname());

        userService.updateUser(id, userRequestDto);
        return ResponseEntity
             .status(HttpStatus.OK)
            .body(ApiResponse.success(null, "유저를 성공적으로 업데이트 시켰습니다."));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        log.info("유저 삭제 요청 : id={}", id);

        userService.deleteUserById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(null, "유저를 성공적으로 삭제 시켰습니다."));
    }

}
