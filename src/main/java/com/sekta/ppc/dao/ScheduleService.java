package com.sekta.ppc.dao;

import com.sekta.ppc.dao.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    void update(Schedule schedule);

    void insert(Schedule schedule);

    List<Schedule> getByUserId(Long userId);

    List<Schedule> getAllCustomScheduleUsers();

    Schedule selectByUserIdAndDay(Long userId, int day);

    void updateWeekend(Long userId, int weekend);
}
