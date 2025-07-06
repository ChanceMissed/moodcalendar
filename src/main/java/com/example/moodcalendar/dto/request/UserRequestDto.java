package com.example.moodcalendar.dto.request;

import com.example.moodcalendar.domain.User;
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
    private String email;

    private String password;

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
