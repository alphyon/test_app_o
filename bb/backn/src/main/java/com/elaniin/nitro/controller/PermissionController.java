package com.elaniin.nitro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.entity.Permission;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.PermissionService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.elaniin.nitro.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="CRUD Permissions", description = "CRUD for manage all Permissions for System")
@RestController
@RequestMapping(value = "/api/v1/permissions")
public class PermissionController extends GenericBase {

	private Logger log = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	private PermissionService permissionService;

	@GetMapping({ "", "/", "/index" })
	public Map<String, String> index() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("Data", "Permissions");
		return result;
	}

	@ApiOperation(value="List all Permissions availables for Users")
	@GetMapping(value = "/all")
	public ResponseEntity<List<Permission>> readAll() {
		List<Permission> all = new ArrayList<Permission>();
		try {
			log.info("Imprimiendo los Permisos Json");
			all = Utils.getListFromIterator(permissionService.findAll().iterator());
			if (all == null) {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {

			System.out.println(ex);
			log.error("Exception", ex.getMessage());

		}
		return ResponseEntity.ok(all);
	}

	@ApiOperation(value="Function for count all permission from database")
	@GetMapping(value = "/count", produces = "application/json")
	public ResponseEntity<Map<String, Long>> totalityRole() {

		Map<String, Long> result = new HashMap<String, Long>();
		Long count = Long.valueOf(permissionService.countPermission());
		if (count.longValue() > 0) {
			result.put("Entire", count);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value="Function for read permissions by ID from database")
	@GetMapping(value = "/read/{id}")
	public ResponseEntity<Permission> read(@PathVariable("id") Long id) {
		Permission foundPermission = permissionService.findByIdPermission(id);
		if (foundPermission == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(foundPermission);
		}
	}	
	
	@ApiOperation(value="Function for search permissions by ID from database")
	@GetMapping(value = "/search/{name}")
	public ResponseEntity<Permission> searchPermission(@PathVariable("name") String name) {
		Permission foundPermission = permissionService.findByPermissionName(name);
		if (foundPermission == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(foundPermission);
		}
	} 
	
	@ApiOperation(value="Function for create permissions")
	@PostMapping(value = "/create")
	public ResponseEntity<Object> create(@RequestBody Permission permission, WebRequest request)
			throws JsonProcessingException {
		Permission created;
		try {
			created = permissionService.save(permission);
			if (created == null) {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, created);
	}

	@ApiOperation(value="Function for updated permissions")
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> update(@ApiParam(value="Permission ID from which permission will be updated from database", required = true, example="1") @PathVariable("id") Long id, @RequestBody Permission permission,  WebRequest request) throws JsonProcessingException {
		ResponseEntity<Permission> result = null;
		try {
			Permission temp = permissionService.findByIdPermission(id);
			if (temp == null) {
				result = ResponseEntity.badRequest().build();
			}
			if (permission == null) {
				result = ResponseEntity.notFound().build();
			} else {
				log.info("Se actualizara el registro.");
				Permission updated = permissionService.update(permission, id);
				result = ResponseEntity.ok(updated);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, result);
	}

	@ApiOperation(value="Function for deleted permissions")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@ApiParam(value="Permission ID from which permission will be updated from database", required = true, example="1") @PathVariable Long id, WebRequest request) throws JsonProcessingException {
		boolean deleted = false;
		deleted = permissionService.delete(id);
		if (!deleted) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, deleted);
	}
}
