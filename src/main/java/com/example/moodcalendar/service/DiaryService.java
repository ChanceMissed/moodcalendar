package com.example.moodcalendar.service;

import com.example.moodcalendar.domain.Diary;
import com.example.moodcalendar.mapper.DiaryMapper;
import com.example.moodcalendar.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DiaryService {

    private final DiaryMapper diaryMapper;


    public DiaryService(DiaryMapper diaryMapper) {
        this.diaryMapper = diaryMapper;
    }

}
