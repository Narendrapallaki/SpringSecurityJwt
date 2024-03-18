package com.springsecurity.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.Entity.User;
import com.springsecurity.Entity.UserCradentials;
import com.springsecurity.UserRepo.UserRepository;
import com.springsecurity.customException.UserIdNotFound;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	@Autowired
	public JwtService jwtService;

	@Autowired
	public PasswordEncoder encoder;

	public void postUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));

		System.out.println(user);
		userRepository.save(user);
	}

	public User getUser(String email) {
		User byEmail = userRepository.findByEmail(email).orElseThrow(() -> new UserIdNotFound("Id not found...!"));

		// System.out.println("get user :"+byEmail);
		return byEmail;
	}

	public String generateToken(UserCradentials userDetails) {
		User user = getUser(userDetails.getUserName());

		// System.out.println(user);

		return jwtService.generateToken(userDetails.getUserName(), user.getRole());

	}
}
