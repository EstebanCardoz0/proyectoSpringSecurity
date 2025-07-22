package com.example.springsecurity.service;

import com.example.springsecurity.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> findAll();

    Optional<Role> findById(Long id);

    Role save(Role rol);

    void deleteById(Long id);

    Role update(Role rol);
}
