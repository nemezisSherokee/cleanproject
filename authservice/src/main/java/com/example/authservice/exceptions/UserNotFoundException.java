package com.example.authservice.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String invalid_id_and_password) {
        super(invalid_id_and_password);
    }
}
