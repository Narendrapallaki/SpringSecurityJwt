
package com.springsecurity.securityConfig;

import java.io.IOException;
import java.io.PrintWriter;



import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        PrintWriter writer = response.getWriter();
	        log.info("Spring enty point");
	        writer.println("Access Denied with error message : " + authException.getMessage());
	}

}
