package com.accolite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accolite.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
