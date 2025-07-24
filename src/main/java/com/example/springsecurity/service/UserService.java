package com.example.springsecurity.service;

import com.example.springsecurity.model.UserSec;
import com.example.springsecurity.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepo;

    @Override
    public List<UserSec> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserSec save(UserSec user) {
        return userRepo.save(user);
    }

    @Override
    public void update(UserSec user) {
        save(user);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
