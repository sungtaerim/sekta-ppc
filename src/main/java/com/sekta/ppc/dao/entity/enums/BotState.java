package com.sekta.ppc.dao.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BotState {

    NORMAL(1),

    REGISTER_WAIT(2),
    PASSWORD_WAIT(3),

    STUDENTS_WAIT_ADD(4),
    STUDENTS_WAIT_DELETE(5),

    SCHEDULE_WAIT(6),
    SCHEDULE_MON(7),
    SCHEDULE_TUE(8),
    SCHEDULE_WEN(9),
    SCHEDULE_THU(10),
    SCHEDULE_FRI(11),
    SCHEDULE_SUN(12),

    WEEKEND_WAIT(13),
    WEEKEND_CHANGE(14),

    SETTINGS_WAIT(15);


    @Getter
    Integer state;
}
