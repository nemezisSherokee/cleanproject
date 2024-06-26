package com.example.authservice.service;

import com.example.authservice.entity.User;
import com.example.authservice.exceptions.UserAlreadyExistsException;
import com.example.authservice.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(User user) throws UserAlreadyExistsException;
    User getUserByNameAndPassword(String name, String password) throws UserNotFoundException;
}