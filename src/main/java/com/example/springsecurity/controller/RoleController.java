package com.example.springsecurity.controller;

import com.example.springsecurity.model.Permission;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.service.IPermissionService;
import com.example.springsecurity.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleSer;

    @Autowired
    private IPermissionService permiSer;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleSer.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRolesById(@PathVariable Long id) {
        Optional<Role> role = roleSer.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createRole(@RequestBody Role role) {
        Set<Permission> permiList = new HashSet<>();
        Permission readPermission;

        //recuperar permissions por su id
        for (Permission per : role.getPermissionsList()) {
            readPermission = permiSer.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                //si encuentro, guardo en la lista
                permiList.add(readPermission);
            }
        }
        role.setPermissionsList(permiList);
        Role newRole = roleSer.save(role);
        return ResponseEntity.ok(newRole);
    }
}
