package com.example.moodcalendar.service;

import com.example.moodcalendar.Exception.BadRequestException;
import com.example.moodcalendar.Exception.DuplicateException;
import com.example.moodcalendar.Exception.NotFoundException;
import com.example.moodcalendar.domain.User;
import com.example.moodcalendar.dto.request.UserRequestDto;
import com.example.moodcalendar.dto.response.UserResponseDto;
import com.example.moodcalendar.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional
    public Long registerUser(UserRequestDto dto) {
        log.info("유저 등록 요청 : email={}, nickname={}", dto.getEmail(), dto.getNickname());

        if (userMapper.selectUserByEmail(dto.getEmail()) != null) {
            log.warn("이미 사용중인 이메일 : email={}", dto.getEmail());
            throw new DuplicateException("이미 사용중인 이메일입니다.");
        }

        User user = dto.toEntity();

        userMapper.insertUser(user); // ID 자동 주입됨
        log.info("유저 등록 완료: {}", user.getId());


        return user.getId();
    }
    /**
     * 유저 조회
     * @param id
     * @return UserResponseDto dto
     */
    @Transactional(readOnly = true)
    public UserResponseDto findUserById(Long id){
        log.info("유저 조회 요청 : id={}", id);

        User user = userMapper.selectUserById(id);

        if (user == null) {
            log.warn("유저 없음: id={}", id);
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }

        UserResponseDto userResponseDto = UserResponseDto.from(user);

        return userResponseDto;
    }

    @Transactional
    public void updateUser(Long id, UserRequestDto userRequestDto) {
        log.info("유저 업데이트 요청 : id={}, email={}, nickname={}", id, userRequestDto.getEmail(), userRequestDto.getNickname());

        User user = userMapper.selectUserById(id);

        if (user == null) {
            log.warn("유저 없음: id={}", id);
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }

        user = userRequestDto.toUpdateEntity(id);
        userMapper.updateUser(user);

        log.info("유저 업데이트 완료: id={}, email={}, nickname={}", id, userRequestDto.getEmail(), userRequestDto.getNickname());
    }

    @Transactional
    public void deleteUserById(Long id) {
        log.info("유저 삭제 요청 : id={}", id);
        User user = userMapper.selectUserById(id);

        if (user == null) {
            log.warn("존재하지 않는 유저 삭제 시도: id={}", id);
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }

        userMapper.deleteUserById(id);
        log.info("유저 삭제 완료 : id={}", id);

    }

    @Transactional(readOnly = true)
    public UserResponseDto login(String email,String password) {
        log.info("로그인 시도: email={}", email);

        User user = userMapper.selectUserByEmail(email);

        // 로그인 이메일 검증
        if(user == null){
            log.warn("이메일이 존재 하지 않음: {}", email);
            throw new NotFoundException("로그인시 존재하지 않는 이메일 입니다.");
        }

        // 로그인 비밀번호 검증 (나중에 BCrypt 적용 )
        if(!user.getPassword().equals(password)){
            log.warn("비밀번호 불일치: email: {}", email);
            throw new BadRequestException("비밀번호가 일치하지 않습니다");
        }

        log.info("로그인 시도 성공: id={}, email={}, nickName={}"
            , user.getId(), user.getEmail(), user.getNickname());


        UserResponseDto userResponseDto = UserResponseDto.from(user);
        return userResponseDto;
    }
}
