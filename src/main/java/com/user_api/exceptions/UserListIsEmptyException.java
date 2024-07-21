package com.user_api.exceptions;

public class UserListIsEmptyException extends RuntimeException {
    public UserListIsEmptyException(String message) {
        super(message);
    }
}
