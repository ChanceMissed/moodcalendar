package com.example.moodcalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/diary")
@Controller
public class DiaryViewController {

    @GetMapping("/")
    public String test() {
        return "/test";
    }

    @GetMapping("/view")
    public String diaryView() {
        return "diary/view/diaryView";
    }

    @GetMapping("/write")
    public String diaryWrite() {
        return "diary/write/diaryWrite";
    }

    @GetMapping("/detail/{id}")
    public String diaryDetail(@PathVariable Long id) {
        return "diary/view/diaryDetail";
    }

    @GetMapping("/calendar")
    public String diaryCalendar() {
        return "diary/calendar/diaryCalendar";
    }
}
