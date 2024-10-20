package com.el2.ordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.el2.ordermanagement.services.KeycloakService;

@RestController
@RequestMapping("/api/order-service")
public class OrderController1 {

	@Autowired
	public KeycloakService keycloakService;

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();

	private final WebClient webClient = WebClient.builder().build();
	
	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	@GetMapping("/order1")
	// @ResponseStatus(HttpStatus.OK)
	public String getOrder1() {
		return "Hello, order 1 successfully executed !";
	}

//    @GetMapping("/callToProductService")
//    public String callFrmProduct() {
//        keycloakService.getAccessToken();
//       Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//       HttpHeaders headers = new HttpHeaders();
//       headers.add("Authorization", "Bearer"+jwt.getTokenValue());
//     restTemplate.exchange("http://localhost:8700/api/product-service/con/callToProductService", 
//	   HttpMethod.GET,new HttpEntity<>(headers),String.class);
//       
//       return "Bearer"+jwt.getTokenValue();
//    }

	@GetMapping("/callToProductService")
	public String callFrmProduct() {
		String accessToken = keycloakService.getAccessToken(); // Obtain the access token
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken); // Add a space after "Bearer"
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8767/api/product-service/con/callfromOrderDevice", HttpMethod.GET, entity,
				String.class);

		return response.getBody();
	}

	// String response =
	// webClient.get().uri("http://product-service/api/product-service/con/callfromOrderDevice")
	// String response =
	// webClient.get().uri("http://localhost:8767/api/product-service/con/callfromOrderDevice")
	@GetMapping("/callToProductService/webclient")
	public String callFrmProductWebclient() {
		String accessToken = keycloakService.getAccessToken(); // Obtain the access token
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken); // Add a space after "Bearer"
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String response = webClient.get().uri("http://product-service/api/product-service/con/callfromOrderDevice")
				.headers(header -> header.setBearerAuth(accessToken)).retrieve().bodyToMono(String.class).block();

		return response;
	}
}