package com.example.springsecurity.repository;

import com.example.springsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
