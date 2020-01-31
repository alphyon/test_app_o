package com.elaniin.nitro.repository;

import org.springframework.data.repository.CrudRepository;

import com.elaniin.nitro.entity.Permission;

public interface PermissionDao extends CrudRepository<Permission, Long> {

}
