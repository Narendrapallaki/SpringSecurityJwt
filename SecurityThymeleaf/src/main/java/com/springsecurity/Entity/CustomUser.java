
package com.springsecurity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public String username;
	public String password;
	public String role;

	public CustomUser(User byEmail) {
		this.username = byEmail.getEmail();

		this.password = byEmail.getPassword();
		this.role = byEmail.getRole();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //

		List<GrantedAuthority> list = new ArrayList<>();
        
		list.add(new SimpleGrantedAuthority(role));
		
		 System.out.println("User role :-"+list);
		return list;
	}

	@Override
	public String getPassword() { // TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() { // TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() { // TODO Auto-generated
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // TODO Auto-generated method
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // TODO Auto-generated
		return true;
	}

	@Override
	public boolean isEnabled() { // TODO Auto-generated method stub
		return true;
	}

}
