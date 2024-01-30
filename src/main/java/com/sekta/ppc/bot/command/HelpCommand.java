package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command {

    private final SendMessageService sendMessageService;

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId(), Messages.HELP.getMessage().concat("\n\n").concat(Messages.HELP_COMMANDS.getMessage()));
    }
}
