package com.example.moodcalendar.service.external;

import com.example.moodcalendar.dto.external.ExternalEmojiDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmojiService {

    private final RestTemplate restTemplate;


    public EmojiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 외부 API 호출 URL
    private static final String EMOJI_API_URL = "https://emojihub.yurace.pro/api/all";


    public List<ExternalEmojiDto> fetchExternalEmojis(){
        ResponseEntity<ExternalEmojiDto[]> response
            = restTemplate.getForEntity(EMOJI_API_URL, ExternalEmojiDto[].class);

//        Arrays.asList(Objects.requireNonNull(response.getBody()));
        return Optional.ofNullable(response.getBody())
            .map(Arrays::stream)
            .orElse(Stream.empty())
            .toList();
    }
}
