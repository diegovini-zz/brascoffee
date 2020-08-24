package com.brascoffee.filter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.brascoffee.security.JwtUtil;
import com.brascoffee.service.UserService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserService userService;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	HandlerExceptionResolver resolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
																																				
		String username = null;
		String jwt = null;

		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				
				jwt = authorizationHeader.substring(7);
				username = jwtUtil.extractUsername(jwt);
			}
			catch(Exception e) {
				resolver.resolveException(request, response, AccessDeniedException.class, e);
				
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
		}
		filterChain.doFilter(request, response);

	}

}
