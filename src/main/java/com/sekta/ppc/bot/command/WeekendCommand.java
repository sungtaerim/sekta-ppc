package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class WeekendCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final ScheduleService scheduleService;

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        val state = user != null
                ? user.getState()
                : BotState.NORMAL;

        switch (state) {
            case NORMAL -> {
                userService.updateState(user.getId(), BotState.WEEKEND_WAIT);
                sendMessageService.sendMessage(user.getId(), Messages.WEEKEND_QUESTION.getMessage(), keyboardService.getDaysKeyboard());
            }

            case WEEKEND_WAIT -> {
                int day = getIntDay(update.getMessage().getText());
                if (day == 0) {
                    userService.updateState(user.getId(), BotState.NORMAL);
                    sendMessageService.sendMessage(user.getId(), Messages.WEEKEND_ERROR.getMessage(), keyboardService.getRemoveKeyboard());
                    return;
                }
                scheduleService.updateWeekend(user.getId(), day);
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(user.getId(), Messages.WEEKEND_SUCCESS.getMessage(), keyboardService.getRemoveKeyboard());
            }

            default -> {
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(user.getId(), Messages.ERROR.getMessage(), keyboardService.getRemoveKeyboard());
            }
        }
    }

    private int getIntDay(String text) {
        switch (text) {
            case "ПН" -> {
                return 1;
            }
            case "ВТ" -> {
                return 2;
            }
            case "СР" -> {
                return 3;
            }
            case "ЧТ" -> {
                return 4;
            }
            case "ПТ" -> {
                return 5;
            }
            case "ВС" -> {
                return 7;
            }
            default -> {
                return 0;
            }
        }
    }
}
