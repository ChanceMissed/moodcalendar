package com.example.moodcalendar.controller.api;

import com.example.moodcalendar.dto.common.ApiResponse;
import com.example.moodcalendar.dto.request.LoginRequestDto;
import com.example.moodcalendar.dto.response.UserResponseDto;
import com.example.moodcalendar.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login/Auth API", description="로그인/로그아웃 관련 API")
@Slf4j
@RequestMapping("/api/auth")
@RestController
public class LoginRestController {

    private final UserService userService;

    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(
        @Valid @RequestBody LoginRequestDto loginRequestDto,
        HttpSession session){

        UserResponseDto userResponseDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        session.setAttribute("userId", userResponseDto.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(userResponseDto, "로그인 성공"));
    }

}
