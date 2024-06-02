package ru.kata.spring.boot_security.demo.util;

import java.time.LocalDateTime;

public class UserErrorResponse {
    private String errorMessage;
    private LocalDateTime timestamp;

    public UserErrorResponse() {
    }

    public UserErrorResponse(String message, LocalDateTime timestamp) {
        this.errorMessage = message;
        this.timestamp = timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
