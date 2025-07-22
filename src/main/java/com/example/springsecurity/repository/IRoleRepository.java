package com.example.springsecurity.repository;

import com.example.springsecurity.model.Permission;
import com.example.springsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

}
