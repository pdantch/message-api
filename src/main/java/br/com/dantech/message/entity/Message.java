package br.com.dantech.message.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contacts")
public class Message {
	@Id
	private String id;
	private String name;
	private String email;
	private String text;
	private LocalDateTime created;
}
