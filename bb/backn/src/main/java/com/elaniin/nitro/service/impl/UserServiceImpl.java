package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elaniin.nitro.dto.UsersDTO;
import com.elaniin.nitro.entity.User;
import com.elaniin.nitro.repository.UserDao;
import com.elaniin.nitro.service.UserService;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public User findByIdUsername(long id) {
		return userDao.findById(id).get();
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public synchronized User save(User user) {
		User userTemp = new User();
		userTemp.setUsername(user.getUsername());
		userTemp.setName(user.getName());
		userTemp.setLastname(user.getLastname());
		userTemp.setEmail(user.getEmail());
		userTemp.setEnabled(user.getEnabled());
		userTemp.setPassword(passwordEncoder.encode(user.getPassword()));
		userTemp.setRoles(user.getRoles());
		userTemp.setAttempts(user.getAttempts());
		return userDao.save(userTemp);
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().forEach(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userDao.deleteById(id);
	}

	@Override
	public long countUser() {
		return userDao.count();
	}

	@Override
	public User update(User user, Long id) {
		User userOriginal = this.findByIdUsername(id);

		if (userOriginal != null) {
			if (user.getUsername() == null) {
				user.setUsername(userOriginal.getUsername());
			}
			if (user.getName() == null) {
				user.setName(userOriginal.getName());
			}
			if (user.getLastname() == null) {
				user.setLastname(userOriginal.getLastname());
			}
			if (user.getEnabled() == null) {
				user.setEnabled(userOriginal.getEnabled());
			}
			if (user.getEmail() == null) {
				user.setEmail(userOriginal.getEmail());
			}
			if (user.getPassword() == null) {
				user.setPassword(userOriginal.getPassword());
			}
			if (user.getAttempts() == null) {
				user.setAttempts(userOriginal.getAttempts());
			}
			if (user.getRoles() == null) {
				user.setRoles(userOriginal.getRoles());
			}
		} else {
			log.error("User no exist!!!");
		}

		log.info("Entidad Original: " + userOriginal.toString());
		log.info("Register data: " + String.valueOf(id));
		log.info("Entidad Modificar: " + user.toString());

		return userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {

			user = userDao.findByUsername(username);

			if (user == null) {
				throw new UsernameNotFoundException("Usuario o Contraseña Invalido.");
			}

			// log.info(user.getRoles().get(0).getName());
			// log.info("Roles: " + user.getRoles().toString());
			List<GrantedAuthority> authorities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

			log.info("Roles del usuario");

			// List<GrantedAuthority> authoritiesUser = user.getRoles().stream().map(role ->
			// new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			log.info("Usuario autenticado: " + username);
			log.info("Roles - Prueba: " + getAuthority());
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.getEnabled(), true, true, true, authorities);

		} catch (Exception e) {
			String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
			log.error(error);

			throw new UsernameNotFoundException(error);
		}
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public UsersDTO objUser(long id) {
		UsersDTO usersDTO = null;
		User user = userDao.findById(id).get();
		if (user != null && user.getId() != null) {
			usersDTO = new UsersDTO();
			usersDTO = convertToDTO(user);
		}
		return usersDTO;
	}

	private UsersDTO convertToDTO(User user) {
		UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);
		return usersDTO;
	}

	
}
