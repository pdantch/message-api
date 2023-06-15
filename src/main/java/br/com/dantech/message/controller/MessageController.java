package br.com.dantech.message.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import br.com.dantech.message.entity.Response;
import br.com.dantech.message.service.IMessageService;
import br.com.dantech.message.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private final IMessageService service;

	public MessageController(MessageService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Message>>> getAllMessages() {
		List<Message> messages = this.service.getAllMessages();
		List<EntityModel<Message>> entityModels = new ArrayList<>();

		messages.forEach(m -> {
			EntityModel<Message> entityModel = EntityModel.of(m);
			entityModel.add(linkTo(methodOn(MessageController.class).getMessageById(m.getId())).withSelfRel());
			entityModels.add(entityModel);
		});

		CollectionModel<EntityModel<Message>> collectionModel = CollectionModel.of(entityModels);
		collectionModel.add(linkTo(methodOn(MessageController.class).getAllMessages()).withSelfRel());

		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Message>> getMessageById(@PathVariable("id") String id) {
		Optional<Message> message = this.service.getMessageById(id);

		return message.map(value -> {
			EntityModel<Message> entityModel = EntityModel.of(value);
			entityModel.add(linkTo(methodOn(MessageController.class).getMessageById(id)).withSelfRel());
			return new ResponseEntity<>(entityModel, HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<EntityModel<Response>> createMessage(@RequestBody Message message) {
		EntityModel<Response> entity;
		Response response;

		try {
			response = new Response(this.service.createMessage(message).get(), HttpStatus.CREATED);
			entity = EntityModel.of(response);
			entity.add(linkTo(methodOn(MessageController.class).getMessageById(message.getId())).withSelfRel());
		} catch (Exception e) {
			response = new Response(message, HttpStatus.ALREADY_REPORTED);
			entity = EntityModel.of(response);
		}
		return new ResponseEntity<>(entity, response.status());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") String id) {
		this.service.deleteMessage(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
