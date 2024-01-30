package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.UserTokenService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.Schedule;
import com.sekta.ppc.dao.entity.Student;
import com.sekta.ppc.dao.entity.UserToken;
import com.sekta.ppc.dao.entity.enums.BotState;
import com.sekta.ppc.dao.entity.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class StartCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final StudentService studentService;
    private final UserTokenService userTokenService;

    @Autowired
    public StartCommand(SendMessageService sendMessageService, UserService userService,
                        ScheduleService scheduleService, StudentService studentService, UserTokenService userTokenService) {
        this.sendMessageService = sendMessageService;
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.studentService = studentService;
        this.userTokenService = userTokenService;
    }

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        val state = user != null
            ? user.getState()
            : BotState.NORMAL;

        switch (state) {
            case NORMAL -> {
                if (user != null) {
                    val students = Arrays.toString(studentService.selectAllByUser(user.getId()).stream().map(Student::getName).toArray()).replaceAll("[\\[\\]]", "");
                    sendMessageService.sendMessage(update.getMessage().getChatId(), String.format(Messages.START_RETURN.getMessage(), user.getPpcLogin(), students));
                    break;
                }
                userService.insertUser(new User(
                                                update.getMessage().getChatId(),
                                                update.getMessage().getFrom().getUserName(),
                                                BotState.REGISTER_WAIT));
                sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.START_HELLO.getMessage());
            }

            case REGISTER_WAIT -> {
                val email = update.getMessage().getText();
                userService.updateLogin(email, update.getMessage().getChatId());
                if (userTokenService.checkEmail(email)) {
                    loginExistContinueRegister(user);
                } else {
                    userService.updateState(update.getMessage().getChatId(), BotState.PASSWORD_WAIT);
                    sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.START_PASSWORD.getMessage());
                }
            }

            case PASSWORD_WAIT -> {
                userTokenService.addNewEmail(user.getPpcLogin(), update.getMessage().getText());
                loginExistContinueRegister(user);
            }
        }
    }

    private void loginExistContinueRegister(User user) {
        sendMessageService.sendMessage(user.getId(), Messages.START_SUCCESS_WITHOUT_PASS.getMessage());
        userService.updateState(user.getId(), BotState.NORMAL);
        createDefaultSchedule(user);
        sendMessageService.sendMessage(user.getId(), Messages.SCHEDULE_DEFAULT.getMessage());
        sendMessageService.sendMessage(user.getId(), Messages.ADD_STUDENT_NAMES.getMessage());
        userService.updateState(user.getId(), BotState.STUDENTS_WAIT_ADD);
    }

    private void createDefaultSchedule(User user) {
        for (int i = 1; i <= 7; i++) {
            if (i == 6) continue;
            if (i == 7) {
                scheduleService.insert(
                    new Schedule(
                        Schedule.NO_ID,
                        user,
                        Schedule.morningTimeFromWeekend,
                        Schedule.morningTimeToWeekend,
                        Schedule.eveningTimeFrom,
                        Schedule.eveningTimeTo,
                        false,
                        i));
                continue;
            }
            scheduleService.insert(
                new Schedule(
                    Schedule.NO_ID,
                    user,
                    Schedule.morningTimeFrom,
                    Schedule.morningTimeTo,
                    Schedule.eveningTimeFrom,
                    Schedule.eveningTimeTo,
                    false,
                    i));
        }
    }
}
