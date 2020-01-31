package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.entity.Permission;

public interface PermissionService {
	Permission findByIdPermission(long id);

	Permission findByPermissionName(String name);

	Permission save(Permission permission);
	
	Permission update(Permission permission, Long id);
	
	List<Permission> findAll();

	boolean delete(long id);
	
	long countPermission();
}
