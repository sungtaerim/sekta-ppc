package com.sekta.ppc.dao;

import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.enums.BotState;

import java.util.List;

public interface UserService {

    void insertUser(User user);


    User getUserById(Long id);

    List<User> getAllUsers();

    List<User> getAllUsersByEmail(String email);


    void updateLogin(String login, Long id);

    void updateToken(String token, Long id);

    void updatePassword(Long id, String password);

    void updateSchedule(Long scheduleId, Long userId);

    void updateVacation(Long userId, Boolean vacation);

    void updateMinutes(Long userId, Long min);

    void updateState(Long id, BotState state);
}
