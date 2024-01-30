package com.sekta.ppc.bot.command.admin;

import com.sekta.ppc.bot.command.Command;
import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class AdminGetLogCommand implements Command {

    private final SendMessageService sendMessageService;

    @Override
    public void execute(Update update) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("var/log/tomcat8/ppc-bot.log")));
            sendMessageService.sendMessage(update.getMessage().getChatId(), content);
        } catch (IOException e) {
            e.printStackTrace();
            sendMessageService.sendMessage(update.getMessage().getChatId(), e.getMessage());
        }
    }
}
