package com.sekta.ppc.bot.command;

import com.sekta.ppc.dao.entity.User;
import com.sekta.ppc.dao.entity.enums.BotState;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    void execute(Update update);
}
