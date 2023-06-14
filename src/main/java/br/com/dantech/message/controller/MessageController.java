package br.com.dantech.message.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dantech.message.entity.Message;
import br.com.dantech.message.entity.MessageRecord;
import br.com.dantech.message.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private final MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Message>>> getAllMessages() {
		List<Message> messages = this.messageService.getAllMessages();
		List<EntityModel<Message>> entityModels = new ArrayList<>();

		messages.forEach(m -> {
			EntityModel<Message> entityModel = EntityModel.of(m);
			entityModel.add(linkTo(methodOn(MessageController.class).getMessageById(m.getId())).withSelfRel());
			entityModels.add(entityModel);
		});

		CollectionModel<EntityModel<Message>> collectionModel = CollectionModel.of(entityModels);
		Link selfLink = linkTo(methodOn(MessageController.class).getAllMessages()).withSelfRel();
		collectionModel.add(selfLink);

		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Message>> getMessageById(@PathVariable("id") String id) {
		Optional<Message> message = this.messageService.getMessageById(id);

		return message.map(value -> {
			EntityModel<Message> entityModel = EntityModel.of(value);
			entityModel.add(linkTo(methodOn(MessageController.class).getMessageById(id)).withSelfRel());
			return new ResponseEntity<>(entityModel, HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<EntityModel<MessageRecord>> createMessage(@RequestBody Message message) throws Exception {
		MessageRecord record;

		try {
			message.setCreated(new Date().toInstant().atOffset(ZoneOffset.UTC).toLocalDate());
			record = new MessageRecord(this.messageService.createMessage(message), HttpStatus.CREATED);
		} catch (Exception e) {
			message.setCreated(null);
			record = new MessageRecord(message, HttpStatus.ALREADY_REPORTED);
		}

		EntityModel<MessageRecord> entity;
		entity = EntityModel.of(record);
		Link selfLink = linkTo(methodOn(MessageController.class).getMessageById(record.message().getId())).withSelfRel();
		entity.add(selfLink);

		return new ResponseEntity<>(entity, record.status());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") String id) {
		this.messageService.deleteMessage(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
