package com.elaniin.nitro.dto;

import java.io.Serializable;
import java.util.List;

import com.elaniin.nitro.entity.Permission;
import com.elaniin.nitro.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RoleDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVerssionUID = 1L;
	private Long id;
	private String name;
	@JsonIgnore
	private List<User> users;
	@JsonIgnore
	private List<Permission> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", users=" + users + ", permissions=" + permissions + "]";
	}
}
