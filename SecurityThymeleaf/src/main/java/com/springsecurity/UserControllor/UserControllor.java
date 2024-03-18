package com.springsecurity.UserControllor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.Entity.User;
import com.springsecurity.Entity.UserCradentials;

import com.springsecurity.UserService.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserControllor {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public UserService userService;
	
	
	@GetMapping("/done/{mail}")
	public User getUse(@PathVariable("mail") String mail)
	{
		return userService.getUser(mail);
		
	}
	
	
	@GetMapping("/test")
	public String test()
	{
		log.info("testing message....!");
	 
		String name="nari";
		
		System.out.println("user.......!"+name);
		return "running...!";
	}
	
	
	@PostMapping("/postUser")
	public ResponseEntity<String>postUserData(@RequestBody User user)
	{
		
		
		userService.postUser(user);
		
		return ResponseEntity.ok("User Data saved");
	}
     
	@GetMapping("/get/{email}")
	public ResponseEntity<Object>getUserData(@PathVariable("email") String email)
	{
		
		System.out.println("email from pathvariable :"+email);
		User user = userService.getUser(email);
		System.out.println("get user :"+user.toString());
		//return null;
		return ResponseEntity.ok(user);
	}
    
	
	@PostMapping("/token")
    public ResponseEntity<Map<String,Object>> getToken(@RequestBody UserCradentials authRequest) {
		
		Map<String,Object>response=new HashMap<>();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
               log.info(authRequest.getUserName());
        	String generateToken = userService.generateToken(authRequest);
        	
        	response.put("Message", "User register details");
    		response.put("Result",generateToken);
    		response.put("Status", HttpStatus.OK.value());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        
       } 
  
		return null;
    }
	
	
	 
}
