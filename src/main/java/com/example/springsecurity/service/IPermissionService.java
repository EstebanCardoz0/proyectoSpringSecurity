package com.example.springsecurity.service;

import com.example.springsecurity.model.Permission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IPermissionService {

    List findAll();

    Optional<Permission> findById(Long id);

    Permission save(Permission permi);

    void deleteById(Long id);

    Permission update(Permission permi);


}
