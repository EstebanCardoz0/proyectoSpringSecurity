package com.example.springsecurity.service;

import com.example.springsecurity.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List findAll();

    public Optional findById(Long id);

    public void deleteById(Long id);

    public UserSec save(UserSec user);

    public void update(UserSec user);
}
