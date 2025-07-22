package com.example.springsecurity.service;

import com.example.springsecurity.model.Permission;
import com.example.springsecurity.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private IPermissionRepository permiRepo;

    @Override
    public List<Permission> findAll() {
        return permiRepo.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permiRepo.findById(id);
    }

    @Override
    public Permission save(Permission permi) {
        return permiRepo.save(permi);
    }

    @Override
    public void deleteById(Long id) {
        permiRepo.deleteById(id);
    }

    @Override
    public Permission update(Permission permi) {
        return permiRepo.save(permi);
    }
}
