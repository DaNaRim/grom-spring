package com.lesson3.homework.exceptions;

public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }
}