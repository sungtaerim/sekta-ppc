package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;


public class SettingsCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;
    private final KeyboardService keyboardService;

    @Autowired
    public SettingsCommand(SendMessageService sendMessageService, UserService userService, KeyboardService keyboardService) {
        this.sendMessageService = sendMessageService;
        this.userService = userService;
        this.keyboardService = keyboardService;
    }


    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        val state = user != null
                ? user.getState()
                : BotState.NORMAL;

        switch (state) {
            case NORMAL -> {
                if (user == null) {
                    sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.USER_NOT_FOUND.getMessage());
                    break;
                }
                userService.updateState(update.getMessage().getChatId(), BotState.SETTINGS_WAIT);
                sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.SETTINGS_WAIT.getMessage(), keyboardService.getMinutesKeyboard());
            }

            case SETTINGS_WAIT -> {
                val message = update.getMessage().getText();
                userService.updateMinutes(user.getId(), Long.parseLong(message));
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.SETTINGS_DONE.getMessage(), keyboardService.getRemoveKeyboard());
            }
        }
    }
}
