package com.elaniin.nitro.repository;

import org.springframework.data.repository.CrudRepository;

import com.elaniin.nitro.entity.Role;

public interface RoleDao extends CrudRepository<Role, Long> {
	
}
