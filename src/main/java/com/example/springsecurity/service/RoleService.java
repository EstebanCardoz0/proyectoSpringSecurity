package com.example.springsecurity.service;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepo;

    @Override
    public List findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public Role save(Role rol) {
        return roleRepo.save(rol);
    }

    @Override
    public void deleteById(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public Role update(Role rol) {
        return roleRepo.save(rol);
    }
}
