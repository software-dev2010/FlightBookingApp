package com.dgs.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public boolean login(String email, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, 
				userDetails.getAuthorities());
		
		authenticationManager.authenticate(token); 
		
		boolean result = token.isAuthenticated(); 
		
		if (result) {
			SecurityContextHolder.getContext().setAuthentication(token); 
		}
		
		return result;
	}

}
