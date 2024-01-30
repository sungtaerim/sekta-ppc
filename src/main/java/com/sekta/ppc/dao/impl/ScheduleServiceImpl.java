package com.sekta.ppc.dao.impl;

import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.entity.Schedule;
import com.sekta.ppc.dao.mapper.ScheduleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private final ScheduleMapper mapper;

    @Override
    public void update(Schedule schedule) {
        log.info("update(schedule = {})", schedule);
        mapper.update(schedule);
    }


    @Override
    public void insert(Schedule schedule) {
        log.info("update(schedule = {})", schedule);
        mapper.insert(schedule);
    }


    @Override
    public List<Schedule> getByUserId(Long userId) {
        log.info("selectByUserId(userId = {})", userId);
        return mapper.selectByUserId(userId);
    }

    @Override
    public List<Schedule> getAllCustomScheduleUsers() {
        log.info("selectAllCustomScheduleUsers");
        return mapper.selectAllCustomScheduleUsers();
    }


    @Override
    public Schedule selectByUserIdAndDay(Long userId, int day) {
        log.info("selectByUserIdAndDay(userId = {}, day = {})", userId, day);
        return mapper.selectByUserIdAndDay(userId, day);
    }


    @Override
    public void updateWeekend(Long userId, int weekend) {
        log.info("updateWeekend(userId = {}, weekend = {})", userId, weekend);
        mapper.updateWeekend(userId, weekend);
    }
}
