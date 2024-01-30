package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.dao.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CheckMessageCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;

    @Autowired
    public CheckMessageCommand(SendMessageService sendMessageService, UserService userService) {
        this.sendMessageService = sendMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getFrom().getId());
        sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.INCOMPLETE.getMessage());
    }
}
