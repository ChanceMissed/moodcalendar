package com.example.moodcalendar.dto.response;

import com.example.moodcalendar.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;

    private String email;

    private String nickname;


    /**
     * Entity -> DTO 변환
     * @param user
     * @return
     */
    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .build();
    }
}
