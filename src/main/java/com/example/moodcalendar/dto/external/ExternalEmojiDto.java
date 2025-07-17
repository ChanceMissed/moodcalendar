package com.example.moodcalendar.dto.external;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalEmojiDto {
    private String name;

    private String category;

    private String group;

    private List<String> htmlCode;

    private List<String> unicode;
}
