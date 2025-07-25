package com.example.springsecurity.controller;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.UserSec;
import com.example.springsecurity.service.IRoleService;
import com.example.springsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@PreAuthorize("denyAll()")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userSer;
    @Autowired
    private IRoleService roleSer;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userSer.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userSer.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') and hasPermission('CREATE')  ")
    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
        Set<Role> roleList = new HashSet<Role>();
        Role readRole;

        //encriptamos pass
        userSec.setPassword(userSer.encriptPassword(userSec.getPassword()));

        //recuperar la permission por su ID
        for (Role role : userSec.getRolesList()) {
            readRole = roleSer.findById(role.getId()).orElse(null);

            if (readRole != null) {
                //si encuentro, lo guardo en la lista
                roleList.add(readRole);
            }
        }
        if (!roleList.isEmpty()) {
            userSec.setRolesList(roleList);
            UserSec newUser = userSer.save(userSec);
            return ResponseEntity.ok(newUser);
        }
        return null;
    }

}
