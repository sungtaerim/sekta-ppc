package com.sekta.ppc.bot;

import com.sekta.ppc.bot.command.commandUtils.CommandContainer;
import com.sekta.ppc.bot.command.commandUtils.CommandName;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.UserTokenService;
import com.sekta.ppc.bot.service.impl.SendMessageServiceImpl;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;
import com.sekta.ppc.dao.entity.enums.BotState;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private final String botName = "bot";
    private final String botToken = "token";
    private final String prefix = "/";

    private CommandContainer commandContainer;
    private UserService userService;
    private String commandIdentifier;

    @Autowired
    public Bot(UserService userService,
               StudentService studentService,
               ScheduleService scheduleService,
               UserTokenService userTokenService,
               KeyboardService keyboardService
    ) {
        this.commandContainer = new CommandContainer(new SendMessageServiceImpl(this), userService, studentService, scheduleService, userTokenService, keyboardService);
        this.userService = userService;
        setMenu();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            val message = update.getMessage().getText();
            if (!message.startsWith(prefix)) {
                val user = userService.getUserById(update.getMessage().getChatId());
                commandIdentifier = findCommandIdentifier(user.getState(), message);
            } else {
                commandIdentifier = message.split(" ")[0].toLowerCase();
            }

//            try {
//                execute(SendMessage.builder().chatId(update.getMessage().getChatId()).text("123").build());
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
            commandContainer.retrieveCommand(commandIdentifier).execute(update);


        }
    }

    private String findCommandIdentifier(BotState state, String message) {
        return switch (state) {
            case REGISTER_WAIT, PASSWORD_WAIT -> CommandName.START.getName();

            case STUDENTS_WAIT_ADD -> CommandName.ADD_STUDENT.getName();

            case STUDENTS_WAIT_DELETE -> CommandName.DELETE_STUDENT.getName();

            case SCHEDULE_WAIT, SCHEDULE_MON, SCHEDULE_TUE, SCHEDULE_WEN, SCHEDULE_THU, SCHEDULE_FRI, SCHEDULE_SUN -> CommandName.SCHEDULE.getName();

            case WEEKEND_WAIT, WEEKEND_CHANGE -> CommandName.WEEKEND.getName();

            case SETTINGS_WAIT -> CommandName.SETTINGS.getName();

            default -> message.split(" ")[0].toLowerCase();

        };
    }

    private void setMenu() {
        List<BotCommand> commands = new ArrayList<>();
        for (CommandName command : CommandName.values()) {
            if (!command.isAdminOnly()) {
                commands.add(new BotCommand(command.getName(), command.getDescription()));
            }
        }

        try {
            val c = new SetMyCommands();
            c.setCommands(commands);
            this.execute(c);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
