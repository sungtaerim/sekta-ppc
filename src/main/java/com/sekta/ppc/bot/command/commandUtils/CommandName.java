package com.sekta.ppc.bot.command.commandUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CommandName {

    START("/start", "Начать работу", false),
//    REGISTER("/register", "Изменить логин и пароль ппк"),
    ADD_STUDENT("/add_student", "Добавить ученика для оповещений", false),
    DELETE_STUDENT("/delete_student",  "Удалить оповещения для ученика", false),
    VIEW_ALL("/view_all", "Список учеников с оповещениями", false),
    CHECK_MESSAGE("/check_message", "Проверить новые сообщения", false),
    HELP("/help",  "Помощь", false),

    RESET("/reset", "Вернуться к обычному состоянию (при ошибках)", false),

    SCHEDULE("/schedule", "Настроить время оповещений", false),
    WEEKEND("/weekend", "Добавить выходной", false),
    MUTE("/mute", "Отключить бота на время", false),

    SETTINGS("/settings", "Настроить частоту оповещений", false),

    ADMIN_GET_LOG("/get_log", "", true),
    ADMIN_GET_USERS("/get_users", "", true),
    ADMIN_GET_USERINFO("/get_userinfo", "", true);

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private boolean adminOnly;
}
