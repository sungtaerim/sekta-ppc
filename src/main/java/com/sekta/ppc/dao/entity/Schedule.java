package com.sekta.ppc.dao.entity;

import lombok.*;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private Long id;
    private User user;
    private LocalTime morningFrom;
    private LocalTime morningTo;
    private LocalTime eveningFrom;
    private LocalTime eveningTo;
    private Boolean weekend;
    private Integer dayOfWeek;

    public static final Long NO_ID = -1L;

    public static final LocalTime morningTimeFrom = LocalTime.parse("09:00");
    public static final LocalTime morningTimeFromWeekend = LocalTime.parse("17:00");
    public static final LocalTime morningTimeTo = LocalTime.parse("13:00");
    public static final LocalTime morningTimeToWeekend = LocalTime.parse("20:00");
    public static final LocalTime eveningTimeFrom = LocalTime.parse("18:00");
    public static final LocalTime eveningTimeTo = LocalTime.parse("20:00");

    public static Schedule getDefaultSchedule(User user, int day) {
        return new Schedule(
                -1L,
                user,
                Schedule.morningTimeFrom,
                Schedule.morningTimeTo,
                Schedule.eveningTimeFrom,
                Schedule.eveningTimeTo,
                false,
                day);
    }

    public static Schedule getDefaultWeekendSchedule(User user, int day) {
        return new Schedule(
            -1L,
            user,
            Schedule.morningTimeFromWeekend,
            Schedule.morningTimeToWeekend,
            Schedule.eveningTimeFrom,
            Schedule.eveningTimeTo,
            false,
            day);
    }

    public static Schedule getWeekendSchedule(User user, int day) {
        return new Schedule(
            -1L,
            user,
            null,
            null,
            null,
            null,
            true,
            day);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Schedule {\n");

        sb.append("    id: ").append(id).append("\n");
        sb.append("    dayOfWeek: ").append(dayOfWeek).append(": ");
        sb.append("    morningFrom: ").append(morningFrom).append(", ");
        sb.append("    morningTo: ").append(morningTo).append("; ");
        sb.append("    eveningFrom: ").append(eveningFrom).append(", ");
        sb.append("    eveningTo: ").append(eveningTo).append("; ");
        sb.append("    weekend: ").append(weekend).append("\n");
        sb.append("}");
        return sb.toString();
    }
}