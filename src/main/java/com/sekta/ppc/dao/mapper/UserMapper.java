package com.sekta.ppc.dao.mapper;


import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.enums.BotState;

import java.util.List;

public interface UserMapper {

    void insertUser(User user);


    User selectUserById(Long id);

    List<User> selectAllUsers();

    List<User> selectAllUsersByEmail(String email);

    List<Long> selectAllCustomScheduleUsers();



    void updateLogin(String login, Long id);

    void updateToken(String token, Long id);

    void updatePassword(Long id, String password);

    void updateSchedule(Long scheduleId, Long userId);

    void updateVacation(Long id, Boolean vacation);

    void updateMinutes(Long id, Long minutes);

    void updateState(Long id, BotState state);
}
