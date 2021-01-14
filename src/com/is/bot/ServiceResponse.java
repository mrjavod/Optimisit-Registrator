package com.is.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ServiceResponse {

	private int status;
	private SendMessage message;

	public ServiceResponse() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SendMessage getMessage() {
		return message;
	}

	public void setMessage(SendMessage message) {
		this.message = message;
	}

}
