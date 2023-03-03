package com.dgs.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dgs.flightreservation.entities.User;
import com.dgs.flightreservation.repos.UserRepository;
import com.dgs.flightreservation.services.SecurityService;

@Controller
public class UserController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	// To use logging we need to define a field 
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);  

	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		LOGGER.info("Inside showRegistrationPage()");
		return "login/registerUser";
	}
	
	@PostMapping("/registerUser")
	public String register(@ModelAttribute User user) {
		LOGGER.info("Inside register()" + user);   // User should have toString method
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user); 
		return "login/login";
	}
	
	@RequestMapping("/showLogin")
	public String showLoginPage() {
		LOGGER.info("Inside showLoginPage()");
		return "login/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, ModelMap modelMap) {
		/*
		User user = userRepository.findByEmail(email); 
		LOGGER.info("Inside login() and the email is: " + email);
		if (user.getPassword().equals(password)) {
			return "findFlights";
		} else {
			modelMap.addAttribute("msg", "Invalid email or password. Please try again.");
		}
		*/
		
		boolean loginResponse = securityService.login(email, password);  
		LOGGER.info("Inside login() and the email is: " + email);
		if (loginResponse) {
			return "findFlights";
		} else {
			modelMap.addAttribute("msg", "Invalid email or password. Please try again.");
		}
		return "login/login";
	}
}
