package com.tpe.service;

import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserRequest userRequest) {
    }
}