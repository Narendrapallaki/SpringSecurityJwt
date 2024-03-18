
package com.springsecurity.securityConfig;

import java.io.IOException;



import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.UserService.JwtService;
import com.springsecurity.customException.UserIdNotFound;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	public final JwtService jwtService;

	public final UserDetailsService customUserDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService customUserDetailsService) {
		super();
		this.jwtService = jwtService;
		this.customUserDetailsService = customUserDetailsService;
	}

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("filter class entered...!");
		try {

			String header = request.getHeader("Authorization");
			System.out.println("header in filter class :" + header);
			if (header == null || !header.startsWith("Bearer ")) {
				System.out.println("--------");
				filterChain.doFilter(request, response); // Proceed with the filter chain
				System.out.println("after filter.....!");
				return;
			}
			
			System.out.println("66666666666666666");
			String token = header.substring(7);
			System.out.println("token:-" + token);
			String emailId = this.jwtService.extractEmailId(token);
			System.out.println("emailId :" + emailId);
			if (emailId == null) {
				throw new UserIdNotFound("User not found" + emailId);
			}
			System.out.println("User name after if :" + emailId);

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(emailId);
       System.out.println(userDetails.getPassword());
       log.info("load by userName form filter :{}",userDetails.getUsername());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			 filterChain.doFilter(request, response);

		} catch (Exception e) {
			log.error("Error occurred in JwtAuthenticationFilter", e);
			throw new ServletException(e);
		}

	}
}




/*
package com.springsecurity.securityConfig;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.UserService.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public final JwtService jwtService;

	public final UserDetailsService customUserDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService customUserDetailsService) {
		super();
		this.jwtService = jwtService;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		// Bearer 2352345235sdfrsfgsdfsdf
		log.info(" Header :  {}", requestHeader);
		String username = null;
		String token = null;

		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			// looking good
			token = requestHeader.substring(7);
			try {

				// username = this.jwtHelper.extractUsername(token);

				username = this.jwtService.extractEmailId(token);
              System.out.println(token+" |"+username);
			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} 
		} else {
			logger.info("Invalid Header Value !! ");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// fetch user detail from username
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			log.info("Userdetails :{}",userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
			// Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			// if (validateToken) {

			// set the authentication
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// } else {
			// logger.info("Validation fails !!");
			// }

		} else {
			log.info("error....!");
		}

		filterChain.doFilter(request, response);

	}

}
*/