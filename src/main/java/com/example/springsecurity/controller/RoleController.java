package com.example.springsecurity.controller;

import com.example.springsecurity.model.Permission;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.service.IPermissionService;
import com.example.springsecurity.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@PreAuthorize("denyAll()")
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleSer;

    @Autowired
    private IPermissionService permiSer;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleSer.findAll();
        return ResponseEntity.ok(roles);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRolesById(@PathVariable Long id) {
        Optional<Role> role = roleSer.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') and hasPermission('CREATE')  ")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permiList = new HashSet<Permission>();
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
//este lo hizo Esteban
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/editPermissions/{id}")
    public ResponseEntity<Role> editPermissions(@PathVariable Long id, @RequestBody List<Long> idPermissions) {

        Optional<Role> roleOpt = roleSer.findById(id);

        if (roleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Role role = roleOpt.get();

        Set<Permission> permiList = new HashSet<>();
        for (Long idPer : idPermissions) {
            permiSer.findById(idPer).ifPresent(permiList::add);
        }

        if (permiList.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        role.setPermissionsList(permiList);
        Role updatedRole = roleSer.save(role);


        return ResponseEntity.ok(updatedRole);
    }

    //y este todocode
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {

        Role rol = roleSer.findById(id).orElse(null);
        if (rol!=null) {
            rol = role;
        }

        roleSer.update(rol);
        return ResponseEntity.ok(rol);

    }

}
