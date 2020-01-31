package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.entity.Permission;
import com.elaniin.nitro.repository.PermissionDao;
import com.elaniin.nitro.service.PermissionService;

@Service(value = "permissionService")
public class PermissionServiceImpl implements PermissionService {
	
	private Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Permission findByIdPermission(long id) {
		Permission permission = new Permission();
		if (id > 0) {
			permission = permissionDao.findById(id).get();
		} else {
			log.error("Register No Exists!!");
			return null;
		}
		return permission;
	}

	@Override
	public Permission findByPermissionName(String name) {
		Permission permission = new Permission();
		List<Permission> list = new ArrayList<>();
		if (!name.isEmpty()) {
			permissionDao.findAll().forEach(list::add);
			list = list.stream().filter(permissionLambda->name.equalsIgnoreCase(permissionLambda.getName())).collect(Collectors.toList());
			if (list.size() > 0) {
				permission = list.get(0);
			}
		} else {
			return null;
		}
		return permission;
	}

	@Override
	public synchronized Permission save(Permission permission) {
		return permissionDao.save(permission);
	}

	@Override
	public Permission update(Permission permission, Long id) {
		log.info("Actualizando el registro " + id);
		if (!(id.intValue() > 0)) {
			return null;
		}
		log.info("Se paso porque es mayor a " + id);
		Permission permissionOriginal = permissionDao.findById(id).get();
		log.info("Se encontro el registro permissionOriginal");
		if (permissionOriginal != null) {
			log.info("Hay registro en PermissionOriginal!!");
			if (permission.getName() == null) {
				permission.setName(permissionOriginal.getName());
			}
			/*if (permission.getRoles().isEmpty()) {
				permission.setRoles(permissionOriginal.getRoles());
			}*/
		} else {
			log.error("Permission no Exists!!!");
			return null;
		}
		log.info("Actualizando el registro!!!..");
		return permissionDao.save(permission);
	}

	@Override
	public List<Permission> findAll() {
		List<Permission> list = new ArrayList<>();
		permissionDao.findAll().forEach(list::add);
		return list;
	}

	@Override
	public boolean delete(long id) {
		boolean existCategory = permissionDao.existsById(id);
		if (existCategory) {
			permissionDao.deleteById(id);			
		}
		return existCategory;
	}

	@Override
	public long countPermission() {
		return permissionDao.count();
	}
}
