
package com.springsecurity.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springsecurity.Entity.CustomUser;
import com.springsecurity.Entity.User;
import com.springsecurity.UserRepo.UserRepository;
import com.springsecurity.customException.UserIdNotFound;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		User byEmail = userRepository.findByEmail(username)
				.orElseThrow(() -> new UserIdNotFound("User Not exits in db!"));

		log.info("loadUserByUsername :{}", byEmail.toString());

		return new CustomUser(byEmail);
	}

}
