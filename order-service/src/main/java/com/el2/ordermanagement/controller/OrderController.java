package com.el2.ordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.el2.ordermanagement.services.KeycloakService;

@RestController
@RequestMapping("/api/order-service/test")
public class OrderController {

	
	@Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KeycloakService keycloakService;

    @GetMapping("/callToProductService/webclient")
    public String callFrmProductWebclient() {
        String accessToken = keycloakService.getAccessToken();
        String response = webClientBuilder.build()
            .get()
            .uri("http://product-service/api/product-service/con/callfromOrderDevice")
            .headers(header -> header.setBearerAuth(accessToken))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        
        return response;
    }
}
