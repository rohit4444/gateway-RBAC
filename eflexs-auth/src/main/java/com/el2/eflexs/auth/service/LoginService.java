package com.el2.eflexs.auth.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.el2.eflexs.auth.model.LoginRequest;
import com.el2.eflexs.auth.model.LoginResponse;



@Service
public class LoginService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${spring.security.oauth2.client.provider.heypaw-client.issuer-uri}")
	private String issueUrl;
	
	@Value("${spring.security.oauth2.client.registration.heypaw-client.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.registration.heypaw-client.client-secret}")
	private String clientSecret;
	
	@Value("${spring.security.oauth2.client.registration.heypaw-client.authorization-grant-type}")
	private String grantType;
	
	@Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
	private String tokenEndpoint;
	
	@Value("${heypaw.oauth2.client.provider.keycloak.logout}")
	private String logoutEndpoint;
	
	@Value("${heypaw.oauth2.client.provider.keycloak.token.introspect}")
	private String introspectEndpoint;
	
	@Value("${spring.security.oauth2.client.registration.heypaw-client.client-id}")
	private String client_id;

	public LoginResponse login(LoginRequest loginrequest) {
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<LoginResponse> response = null;
		try {
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("grant_type", grantType);
		map.add("username", loginrequest.getUsername());
		map.add("password", loginrequest.getPassword());
		
		for (String key : map.keySet()) {
		    List<String> values = map.get(key);
		    System.out.println("printed map value **********"+key + ": " + values);
		}
		System.out.println("tokenEndpoint--------"+ tokenEndpoint);
		System.out.println("issueUrl----------"+ issueUrl);

		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
		
		response = restTemplate.postForEntity(tokenEndpoint, httpEntity, LoginResponse.class);
		}
		catch(Exception e){
			//throw new KeycloakException(4017, "Keycloak Service Not Available");
			e.printStackTrace();
		}
		return response.getBody();
	
		
	}

/*
	public ResponseEntity<StatusResponse> logout(TokenRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("refresh_token", request.getToken());
		
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
		
		ResponseEntity<StatusResponse> response = restTemplate.postForEntity(logoutEndpoint, httpEntity, StatusResponse.class);
		
		StatusResponse res = new StatusResponse();
		if(response.getStatusCode().is2xxSuccessful()) {
			res.setMessage("Logged out successfully");
		}
		return new ResponseEntity<>(res,HttpStatus.OK);
		
		
	}


	public ResponseEntity<IntrospectResponse> introspect(TokenRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("token", request.getToken());
		
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
		
		ResponseEntity<IntrospectResponse> response = restTemplate.postForEntity(introspectEndpoint, httpEntity, IntrospectResponse.class);
		return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
	}
	
	*/

}
