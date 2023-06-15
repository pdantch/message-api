package br.com.dantech.message.entity;

import org.springframework.http.HttpStatus;

public record Response(Message message, HttpStatus status) {

}
