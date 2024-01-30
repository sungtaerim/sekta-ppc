package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class UnknownCommand implements Command {

    private final SendMessageService sendMessageService;

    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.NOT_FOUND.getMessage());
        sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.HELP_COMMANDS.getMessage());
    }
}
