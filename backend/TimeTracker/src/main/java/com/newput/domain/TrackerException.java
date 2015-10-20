package com.newput.domain;

public class TrackerException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String messages;

	public TrackerException(String messages) {
		super(messages);
		this.messages = messages;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

}
