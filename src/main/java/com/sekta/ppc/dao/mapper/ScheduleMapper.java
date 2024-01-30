package com.sekta.ppc.dao.mapper;

import com.sekta.ppc.dao.entity.Schedule;

import java.util.List;

public interface ScheduleMapper {

    List<Schedule> selectByUserId(Long userId);

    List<Schedule> selectAllCustomScheduleUsers();

    Schedule selectByUserIdAndDay(Long userId, int day);

    void update(Schedule schedule);

    void updateWeekend(Long userId, int weekend);

    void insert(Schedule schedule);
}
