package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.entity.Role;
import com.elaniin.nitro.repository.RoleDao;
import com.elaniin.nitro.service.RoleService;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

	private Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private RoleDao roleDao;

	@Override
	public Role findByIdRole(long id) {
		Role role = new Role();
		try {
			if (id > 0) {
				role = roleDao.findById(id).get();
			} else {
				return null;
			}
		} catch(Exception ex) {
			role = null;
		}
		return role;
	}

	@Override
	public Role findByRoleName(String name) {
		Role role = new Role();
		List<Role> list = new ArrayList<>();
		if (!name.isEmpty()) {
			roleDao.findAll().forEach(list::add);
			list = list.stream().filter(roleLambda->name.equalsIgnoreCase(roleLambda.getName())).collect(Collectors.toList());
			if (list.size() > 0) {
				role = list.get(0);
			}
		} else {
			return null;
		}
		return role;
	}

	@Override
	public synchronized Role save(Role role) {
		return roleDao.save(role);
	}

	@Override
	public Role update(Role role, Long id) {
		if (!(id.intValue() > 0)) {
			return null;
		}
		Role roleOriginal = roleDao.findById(id).get();
		if (roleOriginal != null) {
			if (role.getName() == null) {
				role.setName(roleOriginal.getName());
			}
			/*if (role.getPermissions() == null && role.getPermissions().isEmpty()) {
				role.setPermissions(roleOriginal.getPermissions());
			}*/
			/*if (role.getUsers() == null && role.getUsers().isEmpty()) {
				role.setUsers(roleOriginal.getUsers());
			}*/
		} else {
			log.error("Role no Exists!!!");
			return null;
		}
		return roleDao.save(role);
	}

	@Override
	public List<Role> findAll() {
		List<Role> list = new ArrayList<>();
		roleDao.findAll().forEach(list::add);
		return list;
	}

	@Override
	public boolean delete(long id) {
		boolean existCategory = roleDao.existsById(id);
		if (existCategory) {
			roleDao.deleteById(id);
		}
		return existCategory;
		
		
	}

	@Override
	public long countRole() {
		return roleDao.count();
	}

	@Override
	public List<Role> findRoleByUsername(String username) {
		List<Role> list = new ArrayList<>();
		roleDao.findAll().forEach(list::add);
		return list;
	}

}
