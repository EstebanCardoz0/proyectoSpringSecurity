package com.example.springsecurity.service;

import com.example.springsecurity.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List<UserSec> findAll();

    public Optional<UserSec> findById(Long id);

    public void deleteById(Long id);

    public UserSec save(UserSec user);

    public void update(UserSec user);

    public String encriptPassword(String password);

}
