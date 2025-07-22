package com.example.springsecurity.service;

import com.example.springsecurity.model.UserSec;
import com.example.springsecurity.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //tenemos User y necesitamos devolver Userdetails

        //traemos el user de la bd
        UserSec userSec = userRepo.findUserEntityByUsername( username).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));

        //con granted authority spring security maneja permisos
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //programacion funcional a full
        //tomamos roles y los convertimos en simplegranted para poder agregarlos a la autority list
        userSec.getRolesList().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        //ahora tenemos que agregar los permisos
        userSec.getRolesList().stream().flatMap(role -> role.getPermissionsList().stream())//aca recorro los permisos de los roles
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

//retornamos el usuario en formato spring security con los datos de nuestro userSec
        return new User(userSec.getUsername(), userSec.getPassword(), userSec.isEnabled(), userSec.isAccountNotExpired(), userSec.isCredentialNotExpired(), userSec.isAccountNotLocked(), authorityList);
    }
}
