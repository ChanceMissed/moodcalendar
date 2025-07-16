package com.example.moodcalendar.dto.request;

import com.example.moodcalendar.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Client -> DTO SERVER
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    private String password;

    @Size(min = 2, max=20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
    @NotBlank(message = "닉네임은 필수 입력값 입니다.")
    private String nickname;


    /**
     * ID 는 DB 에서 AutoIncrement
     * 등록용 (DTO - > Entity 변환)
     * @return
     */
    public User toEntity() {
        return User.builder()
            .email(this.email)
            .password(this.password)
            .nickname(this.nickname)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * 수정용 (id 포함해서 변환)
     * DTO -> Entity 변환
     * @param id
     * @return
     */
    public User toUpdateEntity(Long id) {
        return User.builder()
            .id(id)
            .email(this.email)
            .password(this.password)
            .nickname(this.nickname)
            .updatedAt(LocalDateTime.now())
            .build(); // updatedAt은 따로 처리해도 OK
    }
}
