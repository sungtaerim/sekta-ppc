package com.sekta.ppc.dao.impl;

import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.enums.BotState;
import com.sekta.ppc.dao.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    @Override
    public void insertUser(User user) {
        log.info("insertUser(user = {})", user);
        userMapper.insertUser(user);
    }

    @Override
    public User getUserById(Long id) {
        log.info("getUserById(id = {})", id);
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("getAllUsers()");
        return userMapper.selectAllUsers();
    }

    @Override
    public List<User> getAllUsersByEmail(String email) {
        log.info("getAllUsers()");
        return userMapper.selectAllUsersByEmail(email);
    }

    @Override
    public void updateLogin(String login, Long id) {
        log.info("updateLogin(id = {}, login = {})", id, login);
        userMapper.updateLogin(login, id);
    }

    @Override
    public void updateToken(String token, Long id) {
        log.info("updateUser(token ..., id = {})", id);
        userMapper.updateToken(token, id);
    }

    @Override
    public void updatePassword(Long id, String password) {
        log.info("updateUser(id = {}, password = {})", id, password);
        userMapper.updatePassword(id, password);
    }

    @Override
    public void updateSchedule(Long scheduleId, Long userId) {
        log.info("updateSchedule(scheduleId = {}, userId = {})", scheduleId, userId);
        userMapper.updateSchedule(scheduleId, userId);
    }

    @Override
    public void updateVacation(Long userId, Boolean vacation) {
        log.info("updateVacation(id = {}, vacation = {})", userId, vacation);
        userMapper.updateVacation(userId, vacation);
    }

    @Override
    public void updateMinutes(Long userId, Long min) {
        log.info("updateMinutes(userId = {}, min = {})", userId, min);
        userMapper.updateMinutes(userId, min);
    }

    @Override
    public void updateState(Long id, BotState state) {
        log.info("updateState(id = {}, state = {})", id, state);
        userMapper.updateState(id, state);
    }


    private final UserMapper userMapper;
}
