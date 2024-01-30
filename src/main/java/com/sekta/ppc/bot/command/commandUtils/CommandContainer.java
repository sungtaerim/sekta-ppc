package com.sekta.ppc.bot.command.commandUtils;

import com.google.common.collect.ImmutableMap;
import com.sekta.ppc.bot.command.admin.AdminGetLogCommand;
import com.sekta.ppc.bot.command.admin.AdminGetUserCommand;
import com.sekta.ppc.bot.command.admin.AdminGetUserInfoCommand;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.bot.service.SendMessageService;
import com.sekta.ppc.bot.command.*;
import com.sekta.ppc.bot.service.UserTokenService;
import com.sekta.ppc.dao.ScheduleService;
import com.sekta.ppc.dao.StudentService;
import com.sekta.ppc.dao.UserService;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendBotMessageService, UserService userService,
                            StudentService studentService, ScheduleService scheduleService,
                            UserTokenService userTokenService, KeyboardService keyboardService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getName(), new StartCommand(sendBotMessageService, userService, scheduleService, studentService, userTokenService))
//                .put(CommandName.REGISTER.getName(), new RegisterCommand(sendBotMessageService, userService))
                .put(CommandName.ADD_STUDENT.getName(), new AddStudentCommand(sendBotMessageService, userService, scheduleService, studentService, keyboardService))
                .put(CommandName.DELETE_STUDENT.getName(), new DeleteStudentCommand(sendBotMessageService, studentService, userService, keyboardService))
                .put(CommandName.VIEW_ALL.getName(), new ViewAllCommand(studentService, sendBotMessageService, userService))
                .put(CommandName.CHECK_MESSAGE.getName(), new CheckMessageCommand(sendBotMessageService, userService))
                .put(CommandName.WEEKEND.getName(), new WeekendCommand(sendBotMessageService, userService, keyboardService, scheduleService))
                .put(CommandName.HELP.getName(), new HelpCommand(sendBotMessageService))
                .put(CommandName.RESET.getName(), new ResetCommand(userService, sendBotMessageService))

                .put(CommandName.SCHEDULE.getName(), new ScheduleCommand(sendBotMessageService, userService, scheduleService, keyboardService))
                .put(CommandName.MUTE.getName(), new MuteCommand(userService, sendBotMessageService))

                .put(CommandName.ADMIN_GET_LOG.getName(), new AdminGetLogCommand(sendBotMessageService))
                .put(CommandName.ADMIN_GET_USERS.getName(), new AdminGetUserCommand(sendBotMessageService, userService))
                .put(CommandName.ADMIN_GET_USERINFO.getName(), new AdminGetUserInfoCommand(sendBotMessageService, userService, scheduleService, studentService))

                .put(CommandName.SETTINGS.getName(), new SettingsCommand(sendBotMessageService, userService, keyboardService))

                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}
