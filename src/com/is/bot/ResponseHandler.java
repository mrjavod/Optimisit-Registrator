package com.is.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class ResponseHandler {

	public static ReplyKeyboard englishButton() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		keyboardMarkup.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row1 = new KeyboardRow();
		row1.add("Level: 001:Beginner");
		row1.add("Level: 002:Elementary");
		row1.add("Level: 003:Pre-Intermediate");
		keyboard.add(row1);

		KeyboardRow row2 = new KeyboardRow();
		row2.add("Level: 004:Intermediate");
		row2.add("Level: 005:Upper-Intermediate");
		row2.add("Level: 006:Advanced");
		keyboard.add(row2);

		KeyboardRow row3 = new KeyboardRow();
		row3.add("Qaytish");
		keyboard.add(row3);

		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public static ReplyKeyboard kidsButton() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		keyboardMarkup.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row1 = new KeyboardRow();
		row1.add("Level: 010:Starter");
		row1.add("Level: 011:Beginner");
		row1.add("Level: 012:Elementary");
		keyboard.add(row1);

		KeyboardRow row2 = new KeyboardRow();
		row2.add("Level: 013:Pre-Intermediate");
		row2.add("Level: 014:Intermediate");
		row2.add("Level: 015:Upper-Intermediate");
		keyboard.add(row2);

		KeyboardRow row3 = new KeyboardRow();
		row3.add("Level: 016:Advanced");
		row3.add("Qaytish");
		keyboard.add(row3);

		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public static ReplyKeyboard registateButton() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		keyboardMarkup.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		row.add("Registratsiya qilish");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public static ReplyKeyboard levelButton() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		keyboardMarkup.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		row.add("English");
		row.add("Kid's English");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public static ReplyKeyboard generatorButton() {
		// TODO Auto-generated method stub
		return null;
	}

}