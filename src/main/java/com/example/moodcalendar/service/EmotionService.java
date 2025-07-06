package com.example.moodcalendar.service;

import com.example.moodcalendar.Exception.DuplicateException;
import com.example.moodcalendar.Exception.NotFoundException;
import com.example.moodcalendar.domain.Emotion;
import com.example.moodcalendar.dto.request.EmotionRequestDto;
import com.example.moodcalendar.dto.response.EmotionResponseDto;
import com.example.moodcalendar.mapper.EmotionMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmotionService {

    private final EmotionMapper emotionMapper;

    public EmotionService(EmotionMapper emotionMapper) {
        this.emotionMapper = emotionMapper;
    }


    public Long addEmotion(EmotionRequestDto emotionRequestDto) {
        log.info("감정 등록 요청: name={}", emotionRequestDto.getName());

        // 중복된 감정 이름 확인
        validateDuplicateName(emotionRequestDto.getName());

        Emotion emotion = emotionRequestDto.toEntity();
        emotionMapper.insertEmotion(emotion);

        log.info("감정 등록 성고: id={}, name={}", emotion.getId(), emotion.getName());

        return emotion.getId();
    }

   

    public void updateEmotion(Long id, EmotionRequestDto emotionRequestDto) {
        log.info("감정 수정 요청: id={}, name={}", id, emotionRequestDto.getName());

        Emotion existing = emotionMapper.selectEmotionById(id);
        if (existing == null) {
            log.warn("수정할 감정을 찾을 수 없음: id={}", id);
            throw new NotFoundException("수정할 감정을 찾을 수 없습니다.");
        }

        // 수정 중 이름이 다른 감정 이름과 중복되는지 확인
        validateDuplicateName(emotionRequestDto.getName(), id);

        Emotion emotion = emotionRequestDto.toUpdateEntity(id);
        emotionMapper.updateEmotion(emotion);
        log.info("감정 수정 완료: id={}, name={}", id, emotion.getName());
    }

    /**
     * 모든 감정 조회
     * @return
     */
    public List<EmotionResponseDto> getAllEmotion(){
        log.info("모든 감정 조회");
        return emotionMapper.selectAllEmotion()
            .stream()
            .map(EmotionResponseDto::from)
            .toList();
    }

    /**
     * 감정 단건 ID 조회
     * @param id
     * @return
     */
    public EmotionResponseDto findEmotionById(Long id) {
        Emotion emotion = validateExistsEmotion(id);

        log.info("감정 조회 성공 : name={}", emotion.getName());

        return EmotionResponseDto.from(emotion);
    }

    public void deleteEmotionById(Long id) {
        log.info("감정 삭제 요청 id={}", id);

        // 감정 존재하는지 확인
        Emotion emotion = validateExistsEmotion(id);

        log.info("감정 삭제 성공 : name={}", emotion.getName());

        int deleted = emotionMapper.deleteEmotionById(id);
        if (deleted == 0) {
            log.warn("삭제 실패 : id={}", id);
            throw new RuntimeException("삭제에 실패했습니다.");
        }
        log.info("감정 삭제 성공 : id={}, name={}", id, emotion.getName());
    }



    /**
     *  감정이 존재하는지 확인
     * @param id
     * @return
     */
    private Emotion validateExistsEmotion(Long id) {
        log.info("아이디로 감정 조회: {}", id);
        Emotion emotion = emotionMapper.selectEmotionById(id);

        if (emotion == null) {
            log.warn("해당 감정을 찾을 수 없습니다 아이디:{}", id);
            throw new NotFoundException("해당 감정을 찾을 수 없습니다.");
        }
        return emotion;
    }
    /**
     * 중복된 감정 이름 확인
     * @param name
     */
    private void validateDuplicateName(String name) {
        Emotion existing = emotionMapper.selectEmotionByName(name);
        if (existing != null) {
            log.warn("이미 존재하는 감정 이름입니다. name={}", name);
            throw new DuplicateException("이미 존재하는 감정 이름 입니다.");
        }
    }


    /**
     * 자기 자신(id 제외) 이랑 중복되는 경우
     * @param name
     * @param excludeId
     */
    private void validateDuplicateName(String name, Long excludeId) {
        Emotion existing = emotionMapper.selectEmotionByName(name);
        if (existing != null && !existing.getId().equals(excludeId)) {
            log.warn("다른 감정과 이름 중복 발생: name={}, id={}", name, existing.getId());
            throw new DuplicateException("이미 존재하는 감정 이름입니다.");
        }
    }

}
