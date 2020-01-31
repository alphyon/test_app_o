package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.dto.UsersDTO;
import com.elaniin.nitro.entity.User;

public interface UserService {
	
	UsersDTO objUser(long id);

	User findByIdUsername(long id);

	User findByUsername(String username);

	User save(User user);
	
	User update(User user, Long id);
	
	List<User> findAll();

	void delete(long id);
	
	long countUser();

}
