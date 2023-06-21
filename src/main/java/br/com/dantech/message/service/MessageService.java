package br.com.dantech.message.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.dantech.message.exception.MessageException;
import br.com.dantech.message.model.Message;
import br.com.dantech.message.repository.MessageRepository;

@Service
public class MessageService implements IMessageService {

	private final MessageRepository repository;

	public MessageService(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Message> getAllMessages() {
		return repository.findAll();
	}

	@Override
	public Optional<Message> getMessageById(String string) {
		return repository.findById(string);
	}

	@Override
	public Optional<Message> createMessage(Message message) throws MessageException {
		try {
			message.setCreated(LocalDateTime.now());
			return Optional.of(repository.insert(message));
		} catch (DuplicateKeyException e) {
			message.setCreated(null);
			throw new MessageException(e.getCause().getMessage());
		}
	}

	@Override
	public void deleteMessage(String id) {
		repository.deleteById(id);
	}

}
