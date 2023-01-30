package com.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	String getByRoleName(String roleName);

}
