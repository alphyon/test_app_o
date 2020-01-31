package com.elaniin.nitro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.elaniin.nitro.dto.ChannelDTO;
import com.elaniin.nitro.dto.UsersDTO;
import com.elaniin.nitro.entity.User;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.UserService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.elaniin.nitro.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("api/v1/users")
public class UserController extends GenericBase {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping({ "", "/", "/index" })
	public Map<String, String> index() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("data", "Users");
		return result;
	}
	

	@GetMapping(value = "/all")
	public ResponseEntity<List<User>> readAll() {
		List<User> all = new ArrayList<User>();
		all = Utils.getListFromIterator(userService.findAll().iterator());
		if (all == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(all);
		}
	}

	@GetMapping(value = "/count", produces = "application/json")
	public ResponseEntity<Map<String, Long>> totalityUser() {

		Map<String, Long> result = new HashMap<String, Long>();
		Long count = Long.valueOf(userService.countUser());
		if (count.longValue() > 0) {
			result.put("Entire", count);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<Object> read(@PathVariable("id") Long id, WebRequest request) throws JsonProcessingException {
		User foundColector;
		try {
			foundColector = userService.findByIdUsername(id);
			if (foundColector == null) {
				throw new ResourceNotFoundException(ConstantMessages.WARNING_MESSAGE);
			} 
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, foundColector);
	}
	
	@GetMapping("/readuser/{id}")
	public ResponseEntity<Object> readuser(@PathVariable("id") Long id, WebRequest request) throws JsonProcessingException {
		UsersDTO usersDTO;
		try {
			usersDTO = userService.objUser(id);
			if (usersDTO == null) {
				throw new ResourceNotFoundException(ConstantMessages.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, usersDTO);
	}
	

	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody User user, WebRequest request) throws JsonProcessingException {
		User created;
		try {
			created = userService.save(user);
			if (created == null) {
				throw new ResourceNotFoundException(ConstantMessages.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, created);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody User user, WebRequest request) throws JsonProcessingException {
		User created;
		try {
			created = userService.save(user);
			if (created == null) {
				throw new ResourceNotFoundException(ConstantMessages.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, created);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody User user, WebRequest request) throws JsonProcessingException {
		ResponseEntity<User> result = null;
		User temp = new User();
		try {
			temp = userService.findByIdUsername(id);
			 if (temp != null && user != null) {
				User updated = userService.update(user, id);
				result = ResponseEntity.ok(updated);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, result);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
