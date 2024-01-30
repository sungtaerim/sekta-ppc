package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.sekta.ppc.bot.command.commandUtils.Messages.MUTE;
import static com.sekta.ppc.bot.command.commandUtils.Messages.USER_NOT_FOUND;

@RequiredArgsConstructor
public class MuteCommand implements Command {

    private final UserService userService;
    private final SendMessageService sendMessageService;

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        if (user == null) {
            sendMessageService.sendMessage(update.getMessage().getChatId(), USER_NOT_FOUND.getMessage());
            return;
        }

        if (user.getState() == BotState.NORMAL) {
            userService.updateVacation(user.getId(), true);
            sendMessageService.sendMessage(user.getId(), MUTE.getMessage());
        } else {
            userService.updateState(user.getId(), BotState.NORMAL);
            sendMessageService.sendMessage(user.getId(), Messages.ERROR_RESET.getMessage());
        }
    }
}
