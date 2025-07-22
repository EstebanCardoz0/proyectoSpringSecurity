package com.example.springsecurity.repository;

import com.example.springsecurity.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserSec, Long> {

    //crea la sentencia en base al nombre ingles del metodo
    //tambien se puede hacer mediante query pero en este caso no es necesario
    Optional<UserSec> findUserEntityByUsername(String username);
}

