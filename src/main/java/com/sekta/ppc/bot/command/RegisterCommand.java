package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import com.sekta.ppc.dao.entity.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RegisterCommand implements Command {

    private static final String FIRST_MESSAGE = "Логин:";
    private static final String SECOND_MESSAGE = "Пароль:";
    private static final String THIRD_MESSAGE = "Логин и пароль успешно изменены";

    private final SendMessageService sendMessageService;
    private final UserService userService;

    @Autowired
    public RegisterCommand(SendMessageService sendMessageService, UserService userService) {
        this.sendMessageService = sendMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        val state = user != null
            ? user.getState()
            : BotState.NORMAL;

        switch (state) {
            case NORMAL -> {
                userService.insertUser(new User(
                                                update.getMessage().getChatId(),
                                                update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName(),
                                                BotState.REGISTER_WAIT));
                sendMessageService.sendMessage(update.getMessage().getChatId(), FIRST_MESSAGE);
            }

            case REGISTER_WAIT -> {
                userService.updateToken(update.getMessage().getText(), update.getMessage().getChatId());
                userService.updateState(update.getMessage().getChatId(), BotState.PASSWORD_WAIT);
                sendMessageService.sendMessage(update.getMessage().getChatId(), SECOND_MESSAGE);
            }

            default -> {
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(user.getId(), Messages.ERROR_RESET.getMessage());
            }
        }
    }
}
