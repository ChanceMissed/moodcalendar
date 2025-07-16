package com.example.moodcalendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserViewController {

    /**
     * 로그인 페이지 이동
     * @return
     */
    @GetMapping("/")
    public String login(){
        return "user/login";
    }
    
}
