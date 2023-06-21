package br.com.dantech.message.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "contacts")
public class Message {
	@Id
	private String id;
	private String name;
	private String email;
	private String text;
	private LocalDateTime created;
}
