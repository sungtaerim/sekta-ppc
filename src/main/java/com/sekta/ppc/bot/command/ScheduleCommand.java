package com.sekta.ppc.bot.command;

import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.Schedule;
import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleCommand implements Command {

    private final SendMessageService sendMessageService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final KeyboardService keyboardService;

    @Override
    public void execute(Update update) {
        val user = userService.getUserById(update.getMessage().getChatId());
        val state = user.getState() != null
            ? user.getState()
            : BotState.NORMAL;

        val keyboard = keyboardService.getScheduleKeyboard();

        switch (state) {
            case NORMAL: {
                sendMessageService.sendMessage(user.getId(), Messages.SCHEDULE_FIRST_MESSAGE.getMessage());
                val day = DayOfWeek.valueOf(LocalDate.now().getDayOfWeek().name());
                switch (day) {
                    case MONDAY: {
                        userService.updateState(user.getId(), BotState.SCHEDULE_THU);
                        sendMessageService.sendMessage(user.getId(), DayOfWeek.TUESDAY.name, keyboard);
                        break;
                    }
                    case TUESDAY: {
                        userService.updateState(user.getId(), BotState.SCHEDULE_WEN);
                        sendMessageService.sendMessage(user.getId(), DayOfWeek.WEDNESDAY.name, keyboard);
                        break;
                    }
                    case WEDNESDAY: {
                        userService.updateState(user.getId(), BotState.SCHEDULE_THU);
                        sendMessageService.sendMessage(user.getId(), DayOfWeek.THURSDAY.name, keyboard);
                        break;
                    }
                    case THURSDAY: {
                        userService.updateState(user.getId(), BotState.SCHEDULE_FRI);
                        sendMessageService.sendMessage(user.getId(), DayOfWeek.FRIDAY.name, keyboard);
                        break;
                    }
                    case SATURDAY:
                    case FRIDAY: {
                        userService.updateState(user.getId(), BotState.SCHEDULE_SUN);
                        sendMessageService.sendMessage(user.getId(), DayOfWeek.SUNDAY.name, keyboard);
                        break;
                    }
                    case SUNDAY: {
                        userService.updateState(user.getId(), BotState.SCHEDULE_MON);
                        sendMessageService.sendMessage(user.getId(), DayOfWeek.MONDAY.name, keyboard);
                        break;
                    }
                }
                break;
            }

            case SCHEDULE_MON: {
                String text = update.getMessage().getText();
                this.setSchedule(text, user, keyboard, DayOfWeek.MONDAY.dayInt, DayOfWeek.TUESDAY.name, BotState.SCHEDULE_TUE);
                break;
            }

            case SCHEDULE_TUE: {
                String text = update.getMessage().getText();
                this.setSchedule(text, user, keyboard, DayOfWeek.TUESDAY.dayInt, DayOfWeek.WEDNESDAY.name, BotState.SCHEDULE_WEN);
                break;
            }

            case SCHEDULE_WEN: {
                String text = update.getMessage().getText();
                this.setSchedule(text, user, keyboard, DayOfWeek.WEDNESDAY.dayInt, DayOfWeek.THURSDAY.name, BotState.SCHEDULE_THU);
                break;
            }

            case SCHEDULE_THU: {
                String text = update.getMessage().getText();
                this.setSchedule(text, user, keyboard, DayOfWeek.THURSDAY.dayInt, DayOfWeek.FRIDAY.name, BotState.SCHEDULE_FRI);
                break;
            }

            case SCHEDULE_FRI: {
                String text = update.getMessage().getText();
                this.setSchedule(text, user, keyboard, DayOfWeek.FRIDAY.dayInt, DayOfWeek.SUNDAY.name, BotState.SCHEDULE_SUN);
                break;
            }

            case SCHEDULE_SUN: {
                String text = update.getMessage().getText();
                this.setSchedule(text, user, keyboard, DayOfWeek.SUNDAY.dayInt, DayOfWeek.SUNDAY.name, BotState.NORMAL);
                val removeKeyboard = new ReplyKeyboardRemove();
                removeKeyboard.setRemoveKeyboard(true);
                sendMessageService.sendMessage(user.getId(), Messages.SCHEDULE_SUCCESS.getMessage(), removeKeyboard);
                break;
            }

            default: {
                userService.updateState(user.getId(), BotState.NORMAL);
                sendMessageService.sendMessage(user.getId(), Messages.ERROR_RESET.getMessage());
            }
        }
    }

    @Getter
    @AllArgsConstructor
    public enum DayOfWeek {
        MONDAY(1, "Понедельник:"),
        TUESDAY(2, "Вторник:"),
        WEDNESDAY(3, "Среда:"),
        THURSDAY(4, "Четверг:"),
        FRIDAY(5, "Пятница:"),
        SATURDAY(6, "Воскресенье:"),
        SUNDAY(7, "Воскресенье:"),

        WEEKEND(0, "Выходной"),
        DEFAULT(0, "Обычное расписание");

        private final Integer dayInt;
        private final String name;
    }


    private void setSchedule(String text, User user, ReplyKeyboardMarkup keyboard, int currentDay, String nextDay, BotState nextState) {
        if (text.equals(DayOfWeek.DEFAULT.name)) {
            this.setDefaultSchedule(user);
            userService.updateState(user.getId(), BotState.NORMAL);
            sendMessageService.sendMessage(user.getId(), Messages.SCHEDULE_SUCCESS.getMessage(), keyboardService.getRemoveKeyboard());
            return;
        }

        if (text.equals(DayOfWeek.WEEKEND.name)) {
            scheduleService.update(Schedule.getWeekendSchedule(user, currentDay));
            userService.updateState(user.getId(), nextState);
            if (!nextState.getState().equals(BotState.NORMAL.getState())) {
                sendMessageService.sendMessage(user.getId(), nextDay, keyboard);
            }
            return;
        }


        val parts = text.replace(";", "-").split("\s*-\s*");
        scheduleService.update(new Schedule(
            -1L,
            user,
            LocalTime.parse(parts[0]),
            LocalTime.parse(parts[1]),
            parts.length > 2 ? LocalTime.parse(parts[2]) : null,
            parts.length > 2 ? LocalTime.parse(parts[2]) : null,
            false,
            currentDay));

        userService.updateState(user.getId(), nextState);
        if (!nextState.getState().equals(BotState.NORMAL.getState())) {
            sendMessageService.sendMessage(user.getId(), nextDay, keyboard);
        }
    }


    private void setDefaultSchedule(User user){
        for (int i = 1; i <= 7; i++) {
            if (i == 7) {
                scheduleService.update(Schedule.getDefaultWeekendSchedule(user, i));
                continue;
            }
            scheduleService.update(Schedule.getDefaultSchedule(user, i));
        }
    }

}
