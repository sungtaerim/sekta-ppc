package com.sekta.ppc.bot.service.impl;

import com.sekta.ppc.bot.command.commandUtils.Messages;
import com.sekta.ppc.bot.service.KeyboardService;
import com.sekta.ppc.dao.entity.Schedule;
import com.sekta.ppc.dao.entity.Student;
import lombok.val;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardServiceImpl implements KeyboardService  {


    @Override
    public ReplyKeyboardMarkup getScheduleKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Выходной"));
        row.add(new KeyboardButton("Обычное расписание"));
        rows.add(row);

        row = new KeyboardRow();
        row.add(new KeyboardButton(Schedule.morningTimeFrom + " - " + Schedule.morningTimeTo + "; " + Schedule.eveningTimeFrom + " - " + Schedule.eveningTimeTo));
        row.add(new KeyboardButton(Schedule.morningTimeFrom + " - " + Schedule.morningTimeTo));
        rows.add(row);

        row = new KeyboardRow();
        row.add(new KeyboardButton(Schedule.morningTimeFromWeekend + " - " + Schedule.morningTimeToWeekend));
        row.add(new KeyboardButton(Schedule.eveningTimeFrom + " - " + Schedule.eveningTimeTo));
        rows.add(row);

        keyboard.setKeyboard(rows);

        return keyboard;
    }


    @Override
    public ReplyKeyboardMarkup getDaysKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("ПН"));
        row.add(new KeyboardButton("ВТ"));
        row.add(new KeyboardButton("СР"));
        row.add(new KeyboardButton("ЧТ"));
        row.add(new KeyboardButton("ПТ"));
        row.add(new KeyboardButton("ВС"));
        rows.add(row);

        keyboard.setKeyboard(rows);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }


    @Override
    public ReplyKeyboardMarkup getYesNoKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Да ✅"));
        row.add(new KeyboardButton("Нет ❌"));
        rows.add(row);

        keyboard.setKeyboard(rows);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }


    @Override
    public ReplyKeyboardRemove getRemoveKeyboard() {
        val keyboard = new ReplyKeyboardRemove();
        keyboard.setRemoveKeyboard(true);
        return keyboard;
    }


    @Override
    public ReplyKeyboardMarkup getStudentsKeyboard(List<Student> students) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        students.forEach(s -> {
                    KeyboardRow row = new KeyboardRow();
                    row.add(new KeyboardButton(s.getName()));
                    rows.add(row);
                });

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton(Messages.DELETE_STUDENTS_ALL.getMessage()));
        rows.add(row);

        keyboard.setKeyboard(rows);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }


    @Override
    public ReplyKeyboardMarkup getMinutesKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("5"));
        row.add(new KeyboardButton("10"));
        row.add(new KeyboardButton("15"));
        row.add(new KeyboardButton("20"));
        rows.add(row);

        row = new KeyboardRow();
        row.add(new KeyboardButton("25"));
        row.add(new KeyboardButton("30"));
        row.add(new KeyboardButton("35"));
        row.add(new KeyboardButton("40"));
        rows.add(row);

        row = new KeyboardRow();
        row.add(new KeyboardButton("45"));
        row.add(new KeyboardButton("50"));
        row.add(new KeyboardButton("55"));
        row.add(new KeyboardButton("60"));
        rows.add(row);

        keyboard.setKeyboard(rows);

        return keyboard;
    }
}
