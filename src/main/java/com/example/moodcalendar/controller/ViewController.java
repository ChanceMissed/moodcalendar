package com.example.moodcalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {


    @GetMapping("/")
    public String test(){
        return "/test";
    }

    @GetMapping("/diary/view")
    public String diaryView(){
        return "diary/view/diaryView";
    }
}
