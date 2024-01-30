package com.sekta.ppc.bot.command.commandUtils;

import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public abstract class CommandUtil {

    public final SendMessageService sendMessageService;
    public final UserService userService;
    public final ScheduleService scheduleService;
    public final StudentService studentService;
    public final KeyboardService keyboardService;

    public BotState getCurrentState(Long userId) {
        User user = userService.getUserById(userId);
        return user != null
                ? user.getState()
                : BotState.NORMAL;
    }
}
