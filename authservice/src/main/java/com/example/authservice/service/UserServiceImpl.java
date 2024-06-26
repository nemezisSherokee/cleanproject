package com.example.authservice.service;

import com.example.authservice.entity.User;
import com.example.authservice.entity.UserRepository;
import com.example.authservice.exceptions.UserAlreadyExistsException;
import com.example.authservice.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).isPresent())  {
            throw new UserAlreadyExistsException("User with the provided username and password already exists.");
        }
        userRepository.save(user);
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsernameAndPassword(name, password);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Invalid id and password");
        }
        return user.get();
    }
}