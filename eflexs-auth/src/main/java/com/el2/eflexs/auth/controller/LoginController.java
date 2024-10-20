package com.el2.eflexs.auth.controller;

import java.util.Date;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.el2.eflexs.auth.model.LoginRequest;
import com.el2.eflexs.auth.model.LoginResponse;
import com.el2.eflexs.auth.service.LoginService;


import jakarta.servlet.http.Cookie;

//import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api/authservice/auth")
//@CrossOrigin(origins = {"*"})
public class LoginController {
	
	
	@Autowired
	LoginService loginservice;
	
	public static final String LOGIN_SERVICE = "login-service";
	private int attempt = 1;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
//	@PostMapping("/login")
//	public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginrequest) {
//		return loginservice.login(loginrequest);
//	}
	
	@PostMapping("/login")
	//@Retry(name = LOGIN_SERVICE, fallbackMethod = "loginRetry")
	public String login (@RequestBody LoginRequest loginrequest) {
		System.out.println("retry method called "+attempt++ +" times at "+new Date());
		LoginResponse response = loginservice.login(loginrequest);
		Cookie cookie = new Cookie("jwtToken", "access_token:"+response.getAccess_token()+
				"refresh_token:"+response.getRefresh_token()+"expires_in:"+response.getExpires_in()+
				"refresh_expires_in:"+response.getRefresh_expires_in()+"token_type:"+response.getToken_type());
		return cookie.getValue();
		//return null;
	}
	
	public String loginRetry(@RequestBody LoginRequest loginrequest, Exception ex) throws Exception{
		String token = null;
			return token;
	}
	/*
	@PostMapping("/api/authservice/auth/logout")
	public ResponseEntity<StatusResponse> logout (@RequestBody TokenRequest token) {
		return loginservice.logout(token);
	}
	
	@PostMapping("/api/authservice/auth/introspect")
	public ResponseEntity<IntrospectResponse> introspect(@RequestBody TokenRequest token) {
		return loginservice.introspect(token);
	}
*/
}
