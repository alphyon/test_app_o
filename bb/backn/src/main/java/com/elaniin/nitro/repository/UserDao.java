package com.elaniin.nitro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elaniin.nitro.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
