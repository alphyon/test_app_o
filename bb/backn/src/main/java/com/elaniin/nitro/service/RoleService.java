package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.entity.Role;

public interface RoleService {
	Role findByIdRole(long id);

	Role findByRoleName(String name);

	Role save(Role role);
	
	Role update(Role role, Long id);
	
	List<Role> findRoleByUsername(String username);
	
	List<Role> findAll();

	boolean delete(long id);
	
	long countRole();
}
