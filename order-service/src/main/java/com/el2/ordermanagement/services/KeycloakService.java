package com.el2.ordermanagement.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



@Service
public class KeycloakService {

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String keycloakAuthUrl;

    //@Value("${keycloak.realm}")
    //private String realm;

    @Value("${spring.security.oauth2.client.registration.order-service.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.order-service.client-secret}")
    private String clientSecret;

    public String getAccessToken() {
        String tokenUrl = keycloakAuthUrl;

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, new HttpHeaders());
        Map<String, String> response = restTemplate.postForObject(tokenUrl, request, Map.class);
        return response.get("access_token");
    }
}

