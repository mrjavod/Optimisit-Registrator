package com.is.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.is.utils.ConnectionPool;
import com.is.utils.ISLogger;

public class MyBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {

		SendMessage message = new SendMessage();

		// when text writing
		if (update.hasMessage() && update.getMessage().hasText()) {

			long chat_id = update.getMessage().getChatId();
			String message_text = update.getMessage().getText();

			// start
			if (message_text.equals("/start")) {
				message.setText("Iltimos kalitni yuboring: ");
				message.setChatId(chat_id);
			}

			// Registratsiya qilish
			else if (message_text.equals("Registratsiya qilish")) {
				boolean isAccess = BotService.isAccess(chat_id);
				if (isAccess) {
					message.setChatId(chat_id);
					message.setText("Darajani tanlang: ");
					message.setReplyMarkup(ResponseHandler.levelButton());
				} else {
					message.setChatId(chat_id);
					message.setText("Bu bot siz uchun emas !");
				}
			}

			// back
			else if (message_text.equals("Qaytish")) {
				boolean isAccess = BotService.isAccess(chat_id);
				if (isAccess) {
					message.setChatId(chat_id);
					message.setText("Darajani tanlang: ");
					message.setReplyMarkup(ResponseHandler.levelButton());
				} else {
					message.setChatId(chat_id);
					message.setText("Bu bot siz uchun emas !");
				}
			}

			// English
			else if (message_text.equals("English")) {
				boolean isAccess = BotService.isAccess(chat_id);
				if (isAccess) {
					message.setChatId(chat_id);
					message.setText("O'quvchi darajasi: ");
					message.setReplyMarkup(ResponseHandler.englishButton());
				} else {
					message.setChatId(chat_id);
					message.setText("Bu bot siz uchun emas !");
				}
			}

			// Kid's English
			else if (message_text.equals("Kid's English")) {
				boolean isAccess = BotService.isAccess(chat_id);
				if (isAccess) {
					message.setChatId(chat_id);
					message.setText("O'quvchi darajasi: ");
					message.setReplyMarkup(ResponseHandler.kidsButton());
				} else {
					message.setChatId(chat_id);
					message.setText("Bu bot siz uchun emas !");
				}
			}

			// Level
			else if (message_text.contains("Level:")) {
				boolean isAccess = BotService.isAccess(chat_id);
				if (isAccess) {
					String level_code = message_text.split(":")[1].trim();
					String key = BotService.generator(level_code);
					if(key != null){
						message.setText("Kalit generatsiya qilindi: \n" + key);
					}else{
						message.setText("Tizim xatoligi, birozdan so'ng yana urinib ko'ring ! ");
					}
					message.setChatId(chat_id);
					message.setReplyMarkup(ResponseHandler.registateButton());
				} else {
					message.setChatId(chat_id);
					message.setText("Bu bot siz uchun emas !");
				}
			}

			// registr key
			else {
				ServiceResponse response = BotService.keyState(chat_id, message_text);
				if (response.getStatus() == 0) {
					message.setChatId(chat_id);
					message = response.getMessage();
				} else if (response.getStatus() == 1) {
					message.setChatId(chat_id);
					message = response.getMessage();
					message.setReplyMarkup(ResponseHandler.registateButton());
				}
			}

		}

		else {
			long chat_id = update.getMessage().getChatId();
			message.setChatId(chat_id);
			message.setText("Noaniq habar jo'natildi !");
		}

		// send to user
		try {
			execute(message);
		} catch (TelegramApiException e) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(e));
		}

	}

	@Override
	public String getBotUsername() {
		return ConnectionPool.getBotUsername();
	}

	@Override
	public String getBotToken() {
		return ConnectionPool.getBotToken();
	}

}
