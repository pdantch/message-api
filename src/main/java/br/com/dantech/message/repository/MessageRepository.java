package br.com.dantech.message.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dantech.message.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

}