package br.com.dantech.message.service;

import java.util.List;
import java.util.Optional;

import br.com.dantech.message.exception.MessageException;
import br.com.dantech.message.model.Message;

public interface IMessageService {

	public List<Message> getAllMessages();

	public Optional<Message> getMessageById(String string);

	public Optional<Message> createMessage(Message message) throws MessageException;

	public void deleteMessage(String id);
}
