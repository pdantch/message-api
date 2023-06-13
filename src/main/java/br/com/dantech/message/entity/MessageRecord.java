package br.com.dantech.message.entity;

import org.springframework.http.HttpStatus;

public record MessageRecord(Message message, HttpStatus status) {

}
