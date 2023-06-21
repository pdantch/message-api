package br.com.dantech.message.model;

import org.springframework.http.HttpStatus;

public record ResponseRecord(Message message, HttpStatus status) {

}
