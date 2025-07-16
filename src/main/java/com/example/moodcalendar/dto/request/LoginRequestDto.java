package com.example.moodcalendar.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일 은 필수 입력값 입니다.")
    @Email(message = "이메일 양식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "빈 항목이 존재합니다.")
    private String password;
}
