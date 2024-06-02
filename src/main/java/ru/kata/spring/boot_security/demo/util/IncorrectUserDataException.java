package ru.kata.spring.boot_security.demo.util;

public class IncorrectUserDataException extends RuntimeException{
    public IncorrectUserDataException(String message) {
        super(message);
    }
}
