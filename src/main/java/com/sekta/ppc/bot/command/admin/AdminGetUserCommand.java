package com.sekta.ppc.bot.command.admin;

import com.sekta.ppc.bot.command.Command;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.UserService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class AdminGetUserCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;

    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId(), userService.getAllUsers().toString());
    }
}
