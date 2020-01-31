package com.elaniin.nitro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
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

import com.elaniin.nitro.dto.RoleDTO;
import com.elaniin.nitro.entity.Role;
import com.elaniin.nitro.entity.User;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.RoleService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.elaniin.nitro.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD Role", description = "CRUD for manage all Role for System")
@RestController
@RequestMapping(value = "/api/v1/roles")
public class RoleController extends GenericBase {

	private Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RoleService roleService;

	@ApiOperation(value = "List all Role availables for Users")
	@GetMapping({ "", "/", "/index" })
	public Map<String, String> index() {
		Map<String, String> result = new HashMap<String, String>();
		try {
			result.put("Data", "Roles");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return result;
	}

	@GetMapping("/all")
//	@PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','READ')")
	public ResponseEntity<List<Role>> readAll() {
		List<Role> all = new ArrayList<Role>();
		try {
			logger.info("Imprimiendo los roles Json");
			all = Utils.getListFromIterator(roleService.findAll().iterator());
			if (all == null) {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			System.out.println(ex);
			logger.error("Exception", ex.getMessage());
		}
		return ResponseEntity.ok(all);
	}

	@ApiOperation(value = "Function for get Total of Role")
	@GetMapping(value = "/count", produces = "application/json")
	public ResponseEntity<Map<String, Long>> totalityRole() {

		Map<String, Long> result = new HashMap<String, Long>();
		Long count = Long.valueOf(roleService.countRole());
		if (count.longValue() > 0) {
			result.put("Entire", count);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Function for Read a single Role")
	@GetMapping(value = "/read/{id}")
	public ResponseEntity<Object> read(
			@ApiParam(value = "Role ID from which interpreters will be search from database", required = true, example = "1") @PathVariable("id") Long id,
			WebRequest request) throws JsonProcessingException {
		RoleDTO foundRole;
		try {
			foundRole = convertToDTO(roleService.findByIdRole(id));
			if (foundRole == null) {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, foundRole);

	}

	@ApiOperation(value = "Function for store Role")
	@PostMapping(value = "/create")
	public ResponseEntity<Object> create(@RequestBody Role role, WebRequest request) throws JsonProcessingException {
		Role created;
		try {
			created = roleService.save(role);
			if (created == null) {
				throw new ResourceNotFoundException(ConstantMessages.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, created);
	}

	@ApiOperation(value = "Function for update Interpreters")
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> update(
			@ApiParam(value = "Role ID from which Role will be updated from database", required = true, example = "1") @PathVariable("id") Long id,
			@RequestBody Role role, WebRequest request) throws JsonProcessingException {
		ResponseEntity<Role> result = null;
		try {
			Role temp = roleService.findByIdRole(id);
			if (temp == null) {
				result = ResponseEntity.badRequest().build();
			}
			if (role == null) {
				result = ResponseEntity.notFound().build();
			} else {
				Role updated = roleService.update(role, id);
				result = ResponseEntity.ok(updated);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, result);
	}

	@ApiOperation(value = "Function for delete Role")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(
			@ApiParam(value = "Role ID from which Role will be deleted from database", required = true, example = "1") @PathVariable Long id,
			WebRequest request) throws JsonProcessingException {
		boolean deleted = false;
		deleted = roleService.delete(id);
		if (!deleted) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, deleted);
	}

	@ApiOperation(value = "Function for search Role by Name")
	@GetMapping("/search/role/{name}")
	public ResponseEntity<Object> searchRoleByName(
			@ApiParam(value = "Interpreters Name from which Role will be search", required = true, example = "1") @PathVariable String name,
			WebRequest request) throws JsonProcessingException {
		RoleDTO foundRole;
		try {
			foundRole = convertToDTO(roleService.findByRoleName(name));
			if (foundRole == null) {
				throw new ResourceNotFoundException(ConstantMessages.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, foundRole);
	}

	private RoleDTO convertToDTO(Role role) {
		RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
		return roleDTO;
	}

}
