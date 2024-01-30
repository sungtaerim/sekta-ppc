package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.sekta.ppc.bot.command.commandUtils.Messages.HELP_COMMANDS;

@RequiredArgsConstructor
public class ResetCommand implements Command {

    private final UserService userService;
    private final SendMessageService sendMessageService;

    @Override
    public void execute(Update update) {
        userService.updateState(update.getMessage().getChatId(), BotState.NORMAL);
        userService.updateVacation(update.getMessage().getChatId(), false);
        sendMessageService.sendMessage(update.getMessage().getChatId(), HELP_COMMANDS.getMessage());
    }
}
