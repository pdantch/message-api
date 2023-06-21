package br.com.dantech.message.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dantech.message.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

}