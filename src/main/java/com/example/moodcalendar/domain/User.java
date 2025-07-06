package com.example.moodcalendar.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
