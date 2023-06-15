package br.com.dantech.message.exception;

public class MessageException extends Exception {
	private static final long serialVersionUID = 1L;

	public MessageException(String error) {
		super(error);
	}

}
