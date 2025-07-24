package com.example.springsecurity.controller;

import com.example.springsecurity.model.Permission;
import com.example.springsecurity.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("denyAll()")
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permiSer;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permiSer.findAll();
        return ResponseEntity.ok(permissions);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permi = permiSer.findById(id);
        return permi.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') and hasPermission('CREATE')  ")
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permi) {
        Permission newPermi = permiSer.save(permi);
        return ResponseEntity.ok(newPermi);
    }


}
