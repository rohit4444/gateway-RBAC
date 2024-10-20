package com.el2.product.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/product-service/kc")
public class KeycloakController {
	
	@GetMapping("/admin")
	//@RolesAllowed("adminn")
	public ResponseEntity<String> getAdminDetails() {
		return ResponseEntity.ok("Hello Admin");
	}

	@GetMapping("/user")
	@RolesAllowed("userr")
	public ResponseEntity<String> getUserDetails() {
		return ResponseEntity.ok("Hello User");
	}

}
