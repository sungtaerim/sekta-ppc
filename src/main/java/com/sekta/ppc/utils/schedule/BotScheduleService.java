package com.sekta.ppc.utils.schedule;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.UserToken;
import com.sekta.ppc.dao.entity.enums.BotState;
import com.sekta.ppc.utils.http.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@AllArgsConstructor
public class BotScheduleService {

    @Autowired
    private final SendMessageService sendMessageService;
    @Autowired
    private final ClientRequest clientRequest;
    @Autowired
    private final UserService userService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final ScheduleService scheduleService;

    private final UserToken userToken = UserToken.getInstance();

    private final String errorGetToken = "Не удалось подключиться к ппк";

    //	@Scheduled(cron = "*/30 * * * * *")
    @Scheduled(cron = "0/30 * 9-13,17-20 * * MON-FRI,SUN")
    public void sendMessage() {
        log.debug("sendMessage()");
        if (!checkDay()) return;
        userToken.get()
                .forEach((email, token) -> {
                    try {
                        val allMessages = clientRequest.getMessages(token);
                        val users = userService.getAllUsersByEmail(email);
                        users
                                .stream()
                                .filter(this::checkSchedule)
                                .forEach(user -> {
                                    val students = studentService.selectAllByUser(user.getId());
                                    StringBuilder builder = new StringBuilder();
                                    students.forEach(student -> {
                                        val currentStudent = allMessages.get(student.getName());
                                        if (allMessages.containsKey(student.getName())) {
                                            if (!currentStudent.equals(student.getLastMessage())) {
                                                builder.append(student.getName()).append("\n");
                                                studentService.updateLastMessage(student.getId(), currentStudent);
                                                studentService.updateLastTime(student.getId());
                                            } else if (student.getLastUpdate().plusMinutes(user.getMinutes()).compareTo(LocalDateTime.now()) < 0) {
                                                builder.append(student.getName()).append("\n");
                                                studentService.updateLastTime(student.getId());
                                            }
                                        }
                                    });
                                    if (!builder.toString().isEmpty()) {
                                        log.info("send message to {}: \n{}", user.getName(), builder);
                                        sendMessageService.sendMessage(user.getId(), builder.toString());
                                    }
                                });
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
    }


    @Scheduled(cron = "0 0 20 * * SUN")
    public void checkSchedule() {
        log.info("checkSchedule");
        scheduleService
                .getAllCustomScheduleUsers()
                .forEach(schedule -> {

                });
    }

    private boolean checkSchedule(User user) {
        val schedule = scheduleService.selectByUserIdAndDay(user.getId(), LocalDate.now().getDayOfWeek().getValue());
        val now = LocalTime.now();

        if (schedule.getWeekend() || user.getOnVacation()) return false;

        if (schedule.getEveningFrom() != null) {
            if (now.isBefore(schedule.getEveningTo()) && now.isAfter(schedule.getEveningFrom())) {
                return true;
            }
        }

        return now.isBefore(schedule.getMorningTo()) && now.isAfter(schedule.getMorningFrom());
    }

    private void deleteIrrelevantUsers() {
        userToken
                .get()
                .forEach((email, token) -> {
                    val studentsData = clientRequest.getAllStudents(token);

                    // TODO: получить список id всех актуальных учеников
                    // TODO: получить список id всех текущих учеников по эмейлу токена
                    // TODO: получить разницу между списками
                    // TODO: удалить из бд неактуальных студентов

                });
    }


    private boolean checkDay() {
        val now = LocalDateTime.now();
        val time = now.toLocalTime();
        if (now.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return ((time.isAfter(LocalTime.parse("17:00")) && time.isBefore(LocalTime.parse("20:00"))));
        }
        return (time.isAfter(LocalTime.parse("09:00")) && time.isBefore(LocalTime.parse("13:00")))
                || (time.isAfter(LocalTime.parse("18:00")) && time.isBefore(LocalTime.parse("20:00")));
    }
}
