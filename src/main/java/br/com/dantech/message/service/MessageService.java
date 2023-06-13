package br.com.dantech.message.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.dantech.message.entity.Message;
import br.com.dantech.message.repository.MessageRepository;

@Service
public class MessageService {

	private final MessageRepository messageRepository;

	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	public Optional<Message> getMessageById(String string) {
		return messageRepository.findById(string);
	}

	public Message createMessage(Message message) throws Exception {
		return messageRepository.insert(message);
	}

	public void deleteMessage(String id) {
		messageRepository.deleteById(id);
	}

}
