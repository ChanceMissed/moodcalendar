package com.example.moodcalendar.mapper;

import com.example.moodcalendar.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    int deleteUserById(Long id);

    int updateUser(User users);

    User selectUserById(Long id);

    User selectUserByEmail(String email);
}
