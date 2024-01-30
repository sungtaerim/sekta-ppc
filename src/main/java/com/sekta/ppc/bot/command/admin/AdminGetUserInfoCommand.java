package com.sekta.ppc.bot.command.admin;

import com.sekta.ppc.bot.command.Command;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class AdminGetUserInfoCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final StudentService studentService;

    @Override
    public void execute(Update update) {
        long userId = Long.parseLong(update.getMessage().getText().split(" ")[1]);
        val user = userService.getUserById(userId);
        val schedule = scheduleService.getByUserId(userId);
        val students = studentService.selectAllByUser(userId);

        String message = user.toString().concat("\n\n").concat(students.toString()).concat("\n\n").concat(schedule.toString());

        sendMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}
