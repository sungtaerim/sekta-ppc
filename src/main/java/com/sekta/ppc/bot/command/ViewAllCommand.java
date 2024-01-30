package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.sekta.ppc.bot.command.commandUtils.Messages.*;

public class ViewAllCommand implements Command {

    private final StudentService studentService;
    private final SendMessageService sendMessageService;
    private final UserService userService;

    @Autowired
    public ViewAllCommand(StudentService studentService, SendMessageService sendMessageService, UserService userService) {
        this.studentService = studentService;
        this.sendMessageService = sendMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        if (user == null) {
            sendMessageService.sendMessage(update.getMessage().getChatId(), USER_NOT_FOUND.getMessage());
            return;
        }

        val students = studentService.selectAllByUser(update.getMessage().getFrom().getId());
        if (students.isEmpty()) {
            sendMessageService.sendMessage(update.getMessage().getChatId(), STUDENTS_NOT_FOUND.getMessage());
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(VIEW_ALL_STUDENTS.getMessage());
        students.forEach(student -> builder.append(student.getName()).append("\n"));

        sendMessageService.sendMessage(update.getMessage().getChatId(), builder.toString());
    }
}
