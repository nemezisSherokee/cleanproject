package com.example.authservice.security;

import com.example.authservice.entity.User;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Service
public interface JwtGeneratorInterface {
    Map<String, String> generateToken(User user) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
