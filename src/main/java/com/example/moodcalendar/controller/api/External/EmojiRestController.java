package com.example.moodcalendar.controller.api.External;

import com.example.moodcalendar.dto.external.ExternalEmojiDto;
import com.example.moodcalendar.service.external.EmojiService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/emojis")
@RestController
public class EmojiRestController {

    private final EmojiService emojiService;

    public EmojiRestController(EmojiService emojiService) {
        this.emojiService = emojiService;
    }

    @GetMapping
    public ResponseEntity<List<ExternalEmojiDto>> getAllEmojis() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(emojiService.fetchExternalEmojis());
    }
}
