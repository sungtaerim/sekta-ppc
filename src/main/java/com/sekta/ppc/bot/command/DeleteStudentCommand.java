package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Slf4j
public class DeleteStudentCommand implements Command {

    private final static String FIRST_MESSAGE = "Имя и фамилия ученика из ппк для удаления оповещений:";
    private final static String SECOND_MESSAGE = "Ученик(и) успешно удален(ы)";
    private static final String ERROR_MESSAGE = "Для начала нужно зарегистрироваться - выбери команду /start";
    private String regex = "[(\n+).,;]";

    private final SendMessageService sendMessageService;
    private final StudentService studentService;
    private final UserService userService;
    private final KeyboardService keyboardService;

    @Autowired
    public DeleteStudentCommand(SendMessageService sendMessageService, StudentService studentService, UserService userService,
                                KeyboardService keyboardService) {
        this.sendMessageService = sendMessageService;
        this.studentService = studentService;
        this.userService = userService;
        this.keyboardService = keyboardService;
    }

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        if (user == null) {
            sendMessageService.sendMessage(update.getMessage().getChatId(), ERROR_MESSAGE);
            return;
        }

        switch (user.getState()) {
            case NORMAL -> {
                val keyboard = keyboardService.getStudentsKeyboard(studentService.selectAllByUser(user.getId()));
                userService.updateState(user.getId(), BotState.STUDENTS_WAIT_DELETE);
                sendMessageService.sendMessage(user.getId(), FIRST_MESSAGE, keyboard);
            }

            case STUDENTS_WAIT_DELETE -> {
                val message = update.getMessage().getText();

                if (message.equals(Messages.DELETE_STUDENTS_ALL.getMessage())) {
                    studentService.deleteAllByUserId(user.getId());
                } else {
                    studentService.deleteByName(message);
                }
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(user.getId(), SECOND_MESSAGE, keyboardService.getRemoveKeyboard());
            }

            default -> {
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(user.getId(), Messages.ERROR_RESET.getMessage());
            }
        }
    }
}
