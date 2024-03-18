package com.springsecurity.UserRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	 Optional<User> findByEmail(String email);
}
