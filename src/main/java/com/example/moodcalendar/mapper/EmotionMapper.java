package com.example.moodcalendar.mapper;

import com.example.moodcalendar.domain.Emotion;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmotionMapper {

    List<Emotion> selectAllEmotion();

    Emotion selectEmotionById(Long id);

    Emotion selectEmotionByName(String name);

    int insertEmotion(Emotion emotion);

    int updateEmotion(Emotion emotion);

    int deleteEmotionById(Long id);
}
